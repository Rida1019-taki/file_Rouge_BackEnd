package org.e.filerouge.service.impl;

import lombok.RequiredArgsConstructor;
import org.e.filerouge.repository.ReservationRepository;
import org.e.filerouge.repository.UtilisateurRepository;
import org.e.filerouge.repository.VoitureRepository;
import org.e.filerouge.service.AdminService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UtilisateurRepository users;
    private final VoitureRepository cars;
    private final ReservationRepository reservations;

    @Override
    @Cacheable(value = "stats_users")
    public long countUsers() {
        return users.count();
    }

    @Override
    @Cacheable(value = "stats_cars")
    public long countCars() {
        return cars.count();
    }

    @Override
    @Cacheable(value = "stats_reservations")
    public long countReservations() {
        return reservations.count();
    }
}
