package org.e.filerouge.entity;

import jakarta.persistence.*;
import lombok.*;
import org.e.filerouge.enums.TypeOwner;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "owners")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Owner {

    @Id
    private Long id;
    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private Utilisateur utilisateur;
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.ENUM)
    private TypeOwner typeOwner;
    private String nomEntreprise;
}
