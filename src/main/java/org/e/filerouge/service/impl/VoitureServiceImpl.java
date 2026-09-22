package org.e.filerouge.service.impl;

import lombok.RequiredArgsConstructor;
import org.e.filerouge.dto.auth.voiture.VoitureRequest;
import org.e.filerouge.dto.auth.voiture.VoitureResponse;
import org.e.filerouge.entity.Voiture;
import org.e.filerouge.enums.ListingType;
import org.e.filerouge.enums.Role;
import org.e.filerouge.enums.StatutVoiture;
import org.e.filerouge.exception.BadRequestException;
import org.e.filerouge.exception.ResourceNotFoundException;
import org.e.filerouge.mapper.VoitureMapper;
import org.e.filerouge.repository.CategorieRepository;
import org.e.filerouge.repository.OwnerRepository;
import org.e.filerouge.repository.VilleRepository;
import org.e.filerouge.repository.VoitureRepository;
import org.e.filerouge.service.VoitureService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VoitureServiceImpl implements VoitureService {

    private final VoitureRepository cars;
    private final OwnerRepository owners;
    private final CategorieRepository categories;
    private final VilleRepository villes;
    private final VoitureMapper mapper;

    @Override
    @Cacheable(value = "voitures_all")
    @Transactional(readOnly = true)
    public List<VoitureResponse> findAll() {
        return cars.findAll().stream().map(mapper::toResponse).toList();
    }

    @Override
    @Cacheable(value = "voitures_by_type", key = "#type.name()")
    @Transactional(readOnly = true)
    public List<VoitureResponse> findByListingType(ListingType type) {
        return cars.findByListingType(type).stream().map(mapper::toResponse).toList();
    }

    @Override
    @Cacheable(value = "voitures_mine", key = "#ownerId")
    @Transactional(readOnly = true)
    public List<VoitureResponse> findMine(Long ownerId) {
        return cars.findByOwnerId(ownerId).stream().map(mapper::toResponse).toList();
    }

    @Override
    @Cacheable(value = "voitures", key = "#id")
    @Transactional(readOnly = true)
    public VoitureResponse getById(Long id) {
        return mapper.toResponse(cars.findById(id).orElseThrow(() -> new ResourceNotFoundException("Voiture introuvable")));
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "voitures_all", allEntries = true),
            @CacheEvict(value = "voitures_by_type", allEntries = true),
            @CacheEvict(value = "voitures_mine", key = "#ownerId"),
            @CacheEvict(value = "stats_cars", allEntries = true)
    })
    public VoitureResponse create(VoitureRequest r, Long ownerId) {
        Voiture v = new Voiture();
        fill(v, r);
        ListingType type = effectiveType(v, r);
        validatePrice(type, r);
        v.setOwner(owners.findById(ownerId).orElseThrow(() -> new ResourceNotFoundException("Owner introuvable")));
        v.setCategorie(categories.findById(r.categorieId()).orElseThrow(() -> new ResourceNotFoundException("Catégorie introuvable")));
        v.setVille(villes.findById(r.villeId()).orElseThrow(() -> new ResourceNotFoundException("Ville introuvable")));
        return mapper.toResponse(cars.save(v));
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "voitures", key = "#id"),
            @CacheEvict(value = "voitures_all", allEntries = true),
            @CacheEvict(value = "voitures_by_type", allEntries = true),
            @CacheEvict(value = "voitures_mine", key = "#ownerId")
    })
    public VoitureResponse update(Long id, VoitureRequest r, Long ownerId) {
        Voiture v = cars.findById(id).orElseThrow(() -> new ResourceNotFoundException("Voiture introuvable"));
        if (!v.getOwner().getId().equals(ownerId)) {
            throw new BadRequestException("Cette voiture ne vous appartient pas");
        }
        fill(v, r);
        ListingType type = effectiveType(v, r);
        validatePrice(type, r);
        v.setCategorie(categories.findById(r.categorieId()).orElseThrow());
        v.setVille(villes.findById(r.villeId()).orElseThrow());
        return mapper.toResponse(cars.save(v));
    }

    private void fill(Voiture v, VoitureRequest r) {
        v.setMarque(r.marque());
        v.setModele(r.modele());
        v.setAnnee(r.annee());
        v.setImmatriculation(r.immatriculation());
        v.setCouleur(r.couleur());
        v.setNombrePlaces(r.nombrePlaces());
        v.setTransmission(r.transmission());
        v.setPrixParJour(r.prixParJour());
        v.setPrixVente(r.prixVente());
        if (r.listingType() != null) {
            v.setListingType(r.listingType());
        }
        if (v.getStatut() == null) {
            v.setStatut(StatutVoiture.DISPONIBLE);
        }
    }

    private ListingType effectiveType(Voiture v, VoitureRequest r) {
        if (r.listingType() != null) {
            return r.listingType();
        }
        return v.getListingType() != null ? v.getListingType() : ListingType.RENTAL;
    }

    private void validatePrice(ListingType type, VoitureRequest r) {
        if (type == ListingType.SALE && r.prixVente() == null) {
            throw new BadRequestException("Le prix de vente est obligatoire pour une annonce de vente");
        }
        if (type == ListingType.RENTAL && r.prixParJour() == null) {
            throw new BadRequestException("Le prix par jour est obligatoire pour une annonce de location");
        }
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "voitures", key = "#id"),
            @CacheEvict(value = "voitures_all", allEntries = true),
            @CacheEvict(value = "voitures_by_type", allEntries = true),
            @CacheEvict(value = "voitures_mine", key = "#userId"),
            @CacheEvict(value = "stats_cars", allEntries = true)
    })
    public void delete(Long id, Long userId, Role role) {
        Voiture v = cars.findById(id).orElseThrow(() -> new ResourceNotFoundException("Voiture introuvable"));
        if (role != Role.ADMIN && !v.getOwner().getId().equals(userId)) {
            throw new BadRequestException("Non autorisé");
        }
        cars.delete(v);
    }
}
