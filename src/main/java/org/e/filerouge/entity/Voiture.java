package org.e.filerouge.entity;

import jakarta.persistence.*;
import lombok.*;
import org.e.filerouge.enums.ListingType;
import org.e.filerouge.enums.StatutVoiture;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.*;

@Entity
@Table(name = "voitures")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Voiture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String marque;
    @Column(nullable = false)
    private String modele;
    private Integer annee;
    private String immatriculation;
    private String couleur;
    private Integer nombrePlaces;
    private String transmission;
    private BigDecimal prixParJour;
    private BigDecimal prixVente;
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private ListingType listingType = ListingType.RENTAL;
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private StatutVoiture statut = StatutVoiture.DISPONIBLE;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Owner owner;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Categorie categorie;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Ville ville;
    @OneToMany(mappedBy = "voiture", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Image> images = new ArrayList<>();
}
