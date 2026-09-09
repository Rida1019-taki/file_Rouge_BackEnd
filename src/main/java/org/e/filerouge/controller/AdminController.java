package org.e.filerouge.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService service;

    @GetMapping("/stats")
    public Map<String, Long> stats() {
        return Map.of("users", service.countUsers(), "cars", service.countCars(), "reservations", service.countReservations());
    }
}
