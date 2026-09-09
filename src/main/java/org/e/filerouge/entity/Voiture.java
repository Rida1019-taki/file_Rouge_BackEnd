package org.e.filerouge.entity;

import jakarta.persistence.*;
import lombok.*;
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
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private StatutVoiture statut = StatutVoiture.DISPONIBLE;
    @ManyToOne(optional = false)
    private Owner owner;
    @ManyToOne(optional = false)
    private Categorie categorie;
    @ManyToOne(optional = false)
    private Ville ville;
    @OneToMany(mappedBy = "voiture", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images = new ArrayList<>();
}
