package org.e.filerouge.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.e.filerouge.dto.auth.reservation.ReservationRequest;
import org.e.filerouge.dto.auth.reservation.ReservationResponse;
import org.e.filerouge.entity.Utilisateur;
import org.e.filerouge.service.ReservationService;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService service;

    @PostMapping
    public ReservationResponse create(@Valid @RequestBody ReservationRequest r, Authentication a) {
        return service.create(r, ((Utilisateur) a.getPrincipal()).getId());
    }

    @GetMapping("/my")
    public Object my(Authentication a) {
        return service.findMyReservations(((Utilisateur) a.getPrincipal()).getId());
    }

    @GetMapping("/owner")
    public Object owner(Authentication a) {
        return service.findOwnerReservations(((Utilisateur) a.getPrincipal()).getId());
    }

    @PatchMapping("/{id}/status/{status}")
    public ReservationResponse status(@PathVariable Long id, @PathVariable String status) {
        return service.updateStatus(id, status);
    }
}
