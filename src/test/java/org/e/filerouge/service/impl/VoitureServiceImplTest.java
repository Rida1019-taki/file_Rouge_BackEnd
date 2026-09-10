package org.e.filerouge.service.impl;

import org.e.filerouge.dto.auth.voiture.VoitureRequest;
import org.e.filerouge.dto.auth.voiture.VoitureResponse;
import org.e.filerouge.entity.Categorie;
import org.e.filerouge.entity.Owner;
import org.e.filerouge.entity.Utilisateur;
import org.e.filerouge.entity.Ville;
import org.e.filerouge.entity.Voiture;
import org.e.filerouge.enums.StatutVoiture;
import org.e.filerouge.mapper.VoitureMapper;
import org.e.filerouge.repository.CategorieRepository;
import org.e.filerouge.repository.OwnerRepository;
import org.e.filerouge.repository.VilleRepository;
import org.e.filerouge.repository.VoitureRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VoitureServiceImplTest {

    @Mock
    private VoitureRepository voitureRepository;

    @Mock
    private OwnerRepository ownerRepository;

    @Mock
    private CategorieRepository categorieRepository;

    @Mock
    private VilleRepository villeRepository;

    @Mock
    private VoitureMapper voitureMapper;

    @InjectMocks
    private VoitureServiceImpl voitureService;

    @Test
    void testCreateVoiture_Success() {
        // Arrange
        VoitureRequest request = new VoitureRequest("Toyota", "Corolla", 2022, "1234-A-1", "Noire", 5, "Automatique", BigDecimal.valueOf(300), 1L, 1L);
        Long ownerId = 1L;

        Owner owner = new Owner();
        owner.setId(ownerId);
        Utilisateur user = new Utilisateur();
        user.setId(ownerId);
        owner.setUtilisateur(user);

        Categorie categorie = new Categorie();
        categorie.setId(1L);

        Ville ville = new Ville();
        ville.setId(1L);

        Voiture voiture = new Voiture();
        voiture.setId(1L);
        voiture.setMarque("Toyota");

        VoitureResponse responseMock = new VoitureResponse(1L, "Toyota", "Corolla", 2022, "1234-A-1", "Noire", 5, "Automatique", BigDecimal.valueOf(300), "DISPONIBLE", 1L, 1L, "Berline", 1L, "Casablanca", null);

        when(ownerRepository.findById(ownerId)).thenReturn(Optional.of(owner));
        when(categorieRepository.findById(1L)).thenReturn(Optional.of(categorie));
        when(villeRepository.findById(1L)).thenReturn(Optional.of(ville));
        when(voitureRepository.save(any(Voiture.class))).thenReturn(voiture);
        when(voitureMapper.toResponse(any(Voiture.class))).thenReturn(responseMock);

        // Act
        VoitureResponse result = voitureService.create(request, ownerId);

        // Assert
        assertNotNull(result);
        assertEquals("Toyota", result.marque());
        assertEquals(1L, result.ownerId());
    }
}
