package org.e.filerouge.repository;

import org.e.filerouge.entity.Categorie;
import org.e.filerouge.entity.Owner;
import org.e.filerouge.entity.Utilisateur;
import org.e.filerouge.entity.Ville;
import org.e.filerouge.entity.Voiture;
import org.e.filerouge.enums.Role;
import org.e.filerouge.enums.StatutVoiture;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class VoitureRepositoryTest {

    @Autowired
    private VoitureRepository voitureRepository;

    @Autowired
    private VilleRepository villeRepository;

    @Autowired
    private CategorieRepository categorieRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Test
    void testFindByVilleIdAndCategorieId() {
        Ville ville = new Ville();
        ville.setNom("Casablanca");
        ville = villeRepository.save(ville);

        Categorie categorie = new Categorie();
        categorie.setNom("Berline");
        categorie = categorieRepository.save(categorie);

        Utilisateur u = new Utilisateur(null, "Nom", "Prenom", "owner@email.com", "0600", "pass", Role.OWNER, true);
        u = utilisateurRepository.save(u);
        Owner owner = new Owner();
        owner.setUtilisateur(u);
        owner = ownerRepository.save(owner);

        Voiture voiture = new Voiture();
        voiture.setMarque("Toyota");
        voiture.setModele("Corolla");
        voiture.setAnnee(2022);
        voiture.setImmatriculation("123-A-1");
        voiture.setCouleur("Noir");
        voiture.setNombrePlaces(5);
        voiture.setTransmission("Auto");
        voiture.setPrixParJour(BigDecimal.valueOf(300));
        voiture.setStatut(StatutVoiture.DISPONIBLE);
        voiture.setVille(ville);
        voiture.setCategorie(categorie);
        voiture.setOwner(owner);

        voitureRepository.save(voiture);

        List<Voiture> result = voitureRepository.findByVilleIdAndCategorieId(ville.getId(), categorie.getId());

        assertEquals(1, result.size());
        assertEquals("Toyota", result.get(0).getMarque());
        assertEquals("Casablanca", result.get(0).getVille().getNom());
    }
}
