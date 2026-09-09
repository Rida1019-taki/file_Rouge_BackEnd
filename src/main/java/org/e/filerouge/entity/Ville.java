package org.e.filerouge.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "villes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ville {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String nom;
}