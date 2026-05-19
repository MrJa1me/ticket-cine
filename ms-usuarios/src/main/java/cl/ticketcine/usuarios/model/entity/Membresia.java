package cl.ticketcine.usuarios.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(
    name = "membresias",
    indexes = {
        @Index(name = "idx_membresia_user_email", columnList = "user_email")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Membresia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "id_membresia")
    private Integer idMembresia;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_email", referencedColumnName = "email", nullable = false)
    private Usuario usuario;

    @Column(name = "nivel", length = 20)
    private String nivel;

    @Column(name = "puntos_acumulados")
    private Integer puntosAcumulados;

    @Column(name = "fecha_vencimiento")
    private LocalDate fechaVencimiento;
}