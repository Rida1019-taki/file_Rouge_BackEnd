package org.e.filerouge.service.impl;

import org.e.filerouge.dto.auth.reservation.ReservationRequest;
import org.e.filerouge.dto.auth.reservation.ReservationResponse;
import org.e.filerouge.entity.Client;
import org.e.filerouge.entity.Reservation;
import org.e.filerouge.entity.Voiture;
import org.e.filerouge.enums.StatutReservation;
import org.e.filerouge.enums.StatutVoiture;
import org.e.filerouge.mapper.ReservationMapper;
import org.e.filerouge.repository.ClientRepository;
import org.e.filerouge.repository.ReservationRepository;
import org.e.filerouge.repository.VoitureRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservationServiceImplTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private VoitureRepository voitureRepository;

    @Mock
    private ReservationMapper reservationMapper;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    @Test
    void testCreateReservation_Success() {
        // Arrange
        Long clientId = 1L;
        Long voitureId = 1L;
        LocalDate dateDebut = LocalDate.now().plusDays(1);
        LocalDate dateFin = LocalDate.now().plusDays(4); // 3 days

        ReservationRequest request = new ReservationRequest(voitureId, dateDebut, dateFin);

        Voiture voiture = new Voiture();
        voiture.setId(voitureId);
        voiture.setStatut(StatutVoiture.DISPONIBLE);
        voiture.setPrixParJour(BigDecimal.valueOf(100));

        Client client = new Client();
        client.setId(clientId);

        Reservation reservation = new Reservation(1L, dateDebut, dateFin, BigDecimal.valueOf(300), StatutReservation.EN_ATTENTE, client, voiture);
        
        ReservationResponse responseMock = new ReservationResponse(1L, dateDebut, dateFin, BigDecimal.valueOf(300), "EN_ATTENTE", clientId, voitureId);

        when(voitureRepository.findById(voitureId)).thenReturn(Optional.of(voiture));
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);
        when(reservationMapper.toResponse(any(Reservation.class))).thenReturn(responseMock);

        // Act
        ReservationResponse result = reservationService.create(request, clientId);

        // Assert
        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(300), result.montantTotal());
        assertEquals(clientId, result.clientId());
        assertEquals(voitureId, result.voitureId());
    }
}
