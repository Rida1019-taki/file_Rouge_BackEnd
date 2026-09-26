package org.e.filerouge.service.impl;

import lombok.RequiredArgsConstructor;
import org.e.filerouge.enums.ListingType;
import org.e.filerouge.repository.ReservationRepository;
import org.e.filerouge.repository.UtilisateurRepository;
import org.e.filerouge.repository.VoitureRepository;
import org.e.filerouge.service.AdminService;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UtilisateurRepository users;
    private final VoitureRepository cars;
    private final ReservationRepository reservations;

    @Override
    public long countUsers() {
        return users.count();
    }

    @Override
    public long countCars() {
        return cars.count();
    }

    @Override
    public long countCarsSale() {
        return cars.countByListingType(ListingType.SALE);
    }

    @Override
    public long countCarsRental() {
        return cars.countByListingType(ListingType.RENTAL);
    }

    @Override
    public long countReservations() {
        return reservations.count();
    }
}
