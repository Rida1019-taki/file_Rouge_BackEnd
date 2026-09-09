package org.e.filerouge.service.impl;

import lombok.RequiredArgsConstructor;
import org.e.filerouge.dto.auth.reservation.ReservationRequest;
import org.e.filerouge.dto.auth.reservation.ReservationResponse;
import org.e.filerouge.entity.Client;
import org.e.filerouge.entity.Reservation;
import org.e.filerouge.entity.Voiture;
import org.e.filerouge.enums.StatutReservation;
import org.e.filerouge.enums.StatutVoiture;
import org.e.filerouge.exception.BadRequestException;
import org.e.filerouge.mapper.ReservationMapper;
import org.e.filerouge.repository.ClientRepository;
import org.e.filerouge.repository.ReservationRepository;
import org.e.filerouge.repository.VoitureRepository;
import org.e.filerouge.service.ReservationService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import java.math.*;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservations;
    private final ClientRepository clients;
    private final VoitureRepository cars;
    private final ReservationMapper mapper;

    @Override
    @Caching(evict = {
            @CacheEvict(value = "client_reservations", key = "#clientId"),
            @CacheEvict(value = "stats_reservations", allEntries = true)
    })
    public ReservationResponse create(ReservationRequest r, Long clientId) {
        if (!r.dateFin().isAfter(r.dateDebut())) {
            throw new BadRequestException("La date de fin doit être après la date de début");
        }
        Voiture v = cars.findById(r.voitureId()).orElseThrow(() -> new ResourceNotFoundException("Voiture introuvable"));
        if (v.getStatut() != StatutVoiture.DISPONIBLE) {
            throw new BadRequestException("Voiture indisponible");
        }
        Client c = clients.findById(clientId).orElseThrow(() -> new ResourceNotFoundException("Client introuvable"));
        long days = ChronoUnit.DAYS.between(r.dateDebut(), r.dateFin());
        Reservation x = new Reservation(null, r.dateDebut(), r.dateFin(), v.getPrixParJour().multiply(BigDecimal.valueOf(days)), StatutReservation.EN_ATTENTE, c, v);
        return mapper.toResponse(reservations.save(x));
    }

    @Override
    @Cacheable(value = "client_reservations", key = "#id")
    public List<ReservationResponse> findMyReservations(Long id) {
        return reservations.findByClientId(id).stream().map(mapper::toResponse).toList();
    }

    @Override
    @Cacheable(value = "owner_reservations", key = "#id")
    public List<ReservationResponse> findOwnerReservations(Long id) {
        return reservations.findByVoitureOwnerId(id).stream().map(mapper::toResponse).toList();
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "client_reservations", allEntries = true),
            @CacheEvict(value = "owner_reservations", allEntries = true)
    })
    public ReservationResponse updateStatus(Long id, String status) {
        Reservation r = reservations.findById(id).orElseThrow(() -> new ResourceNotFoundException("Réservation introuvable"));
        try {
            r.setStatut(StatutReservation.valueOf(status));
        } catch (Exception e) {
            throw new BadRequestException("Statut invalide");
        }
        return mapper.toResponse(reservations.save(r));
    }
}
