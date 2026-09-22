package org.e.filerouge.entity;

import jakarta.persistence.*;
import lombok.*;
import org.e.filerouge.enums.StatutReservation;
import org.e.filerouge.enums.TypeReservation;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "reservations")
@Getter
@Setter
@NoArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(nullable = false)
    private TypeReservation type = TypeReservation.LOCATION;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private BigDecimal montantTotal;
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(nullable = false)
    private StatutReservation statut = StatutReservation.EN_ATTENTE;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "voiture_id")
    private Voiture voiture;

    public Reservation(Long id, TypeReservation type, LocalDate dateDebut, LocalDate dateFin,
                       BigDecimal montantTotal, StatutReservation statut, Client client, Voiture voiture) {
        this.id = id;
        this.type = type;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.montantTotal = montantTotal;
        this.statut = statut;
        this.client = client;
        this.voiture = voiture;
    }
}