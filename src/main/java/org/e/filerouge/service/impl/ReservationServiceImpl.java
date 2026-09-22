package org.e.filerouge.service.impl;

import lombok.RequiredArgsConstructor;
import org.e.filerouge.dto.auth.reservation.ReservationRequest;
import org.e.filerouge.dto.auth.reservation.ReservationResponse;
import org.e.filerouge.entity.Client;
import org.e.filerouge.entity.Reservation;
import org.e.filerouge.entity.Voiture;
import org.e.filerouge.enums.ListingType;
import org.e.filerouge.enums.StatutReservation;
import org.e.filerouge.enums.StatutVoiture;
import org.e.filerouge.enums.TypeReservation;
import org.e.filerouge.exception.BadRequestException;
import org.e.filerouge.exception.ResourceNotFoundException;
import org.e.filerouge.exception.UnauthorizedException;
import org.e.filerouge.mapper.ReservationMapper;
import org.e.filerouge.repository.ClientRepository;
import org.e.filerouge.repository.ReservationRepository;
import org.e.filerouge.repository.VoitureRepository;
import org.e.filerouge.service.ReservationService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservations;
    private final ClientRepository clients;
    private final VoitureRepository cars;
    private final ReservationMapper mapper;

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "client_reservations", key = "#clientId"),
            @CacheEvict(value = "stats_reservations", allEntries = true)
    })
    public ReservationResponse create(ReservationRequest r, Long clientId) {
        TypeReservation type = r.type() != null ? r.type() : TypeReservation.LOCATION;
        Voiture v = cars.findById(r.voitureId()).orElseThrow(() -> new ResourceNotFoundException("Voiture introuvable"));
        if (v.getStatut() != StatutVoiture.DISPONIBLE) {
            throw new BadRequestException("Voiture indisponible");
        }
        Client c = clients.findById(clientId).orElseThrow(() -> new ResourceNotFoundException("Client introuvable"));

        if (type == TypeReservation.ACHAT) {
            if (v.getListingType() != ListingType.SALE) {
                throw new BadRequestException("Cette voiture n'est pas à vendre");
            }
            if (v.getPrixVente() == null) {
                throw new BadRequestException("Prix de vente manquant");
            }
            if (reservations.existsByVoitureIdAndTypeAndStatutIn(v.getId(), TypeReservation.ACHAT,
                    List.of(StatutReservation.EN_ATTENTE, StatutReservation.CONFIRMEE))) {
                throw new BadRequestException("Une demande d'achat est déjà en cours pour cette voiture");
            }
            Reservation achat = new Reservation(null, TypeReservation.ACHAT, null, null, v.getPrixVente(),
                    StatutReservation.EN_ATTENTE, c, v);
            return mapper.toResponse(reservations.save(achat));
        }

        if (r.dateDebut() == null || r.dateFin() == null) {
            throw new BadRequestException("Les dates sont obligatoires pour une location");
        }
        if (!r.dateFin().isAfter(r.dateDebut())) {
            throw new BadRequestException("La date de fin doit être après la date de début");
        }
        if (v.getListingType() != ListingType.RENTAL) {
            throw new BadRequestException("Cette voiture n'est pas à louer");
        }
        if (v.getPrixParJour() == null) {
            throw new BadRequestException("Prix de location manquant");
        }
        long days = ChronoUnit.DAYS.between(r.dateDebut(), r.dateFin());
        BigDecimal montant = v.getPrixParJour().multiply(BigDecimal.valueOf(days));
        Reservation location = new Reservation(null, TypeReservation.LOCATION, r.dateDebut(), r.dateFin(), montant,
                StatutReservation.EN_ATTENTE, c, v);
        return mapper.toResponse(reservations.save(location));
    }

    @Override
    @Cacheable(value = "client_reservations", key = "#id")
    @Transactional(readOnly = true)
    public List<ReservationResponse> findMyReservations(Long id) {
        return reservations.findByClientId(id).stream().map(mapper::toResponse).toList();
    }

    @Override
    @Cacheable(value = "owner_reservations", key = "#id")
    @Transactional(readOnly = true)
    public List<ReservationResponse> findOwnerReservations(Long id) {
        return reservations.findByVoitureOwnerId(id).stream().map(mapper::toResponse).toList();
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "client_reservations", allEntries = true),
            @CacheEvict(value = "owner_reservations", allEntries = true),
            @CacheEvict(value = "voitures", allEntries = true),
            @CacheEvict(value = "voitures_all", allEntries = true),
            @CacheEvict(value = "voitures_by_type", allEntries = true),
            @CacheEvict(value = "voitures_mine", allEntries = true),
            @CacheEvict(value = "stats_reservations", allEntries = true)
    })
    public ReservationResponse updateStatus(Long id, String status) {
        Reservation r = reservations.findById(id).orElseThrow(() -> new ResourceNotFoundException("Réservation introuvable"));
        StatutReservation nouveau;
        try {
            nouveau = StatutReservation.valueOf(status);
        } catch (Exception e) {
            throw new BadRequestException("Statut invalide");
        }
        r.setStatut(nouveau);
        if (nouveau == StatutReservation.CONFIRMEE && r.getType() == TypeReservation.ACHAT) {
            Voiture v = r.getVoiture();
            v.setStatut(StatutVoiture.INACTIVE);
            cars.save(v);
        }
        return mapper.toResponse(reservations.save(r));
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "client_reservations", allEntries = true),
            @CacheEvict(value = "owner_reservations", allEntries = true)
    })
    public ReservationResponse cancel(Long id, Long clientId) {
        Reservation r = reservations.findById(id).orElseThrow(() -> new ResourceNotFoundException("Réservation introuvable"));
        if (!r.getClient().getId().equals(clientId)) {
            throw new UnauthorizedException("Vous n'êtes pas autorisé à annuler cette réservation");
        }
        if (r.getStatut() != StatutReservation.EN_ATTENTE) {
            throw new BadRequestException("Seules les réservations en attente peuvent être annulées");
        }
        r.setStatut(StatutReservation.ANNULEE);
        return mapper.toResponse(reservations.save(r));
    }
}