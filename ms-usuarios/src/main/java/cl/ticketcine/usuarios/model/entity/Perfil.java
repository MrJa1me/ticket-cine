package cl.ticketcine.usuarios.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "perfiles",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_perfil_user_email", columnNames = "user_email")
    },
    indexes = {
        @Index(name = "idx_perfil_user_email", columnList = "user_email")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "id_perfil")
    private Integer idPerfil;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_email", referencedColumnName = "email", nullable = false)
    private Usuario usuario;

    @Column(name = "preferencia_idioma", length = 10)
    private String preferenciaIdioma;

    @Column(name = "es_estudiante")
    private Boolean esEstudiante;

    @Column(name = "biografia", length = 255)
    private String biografia;
}