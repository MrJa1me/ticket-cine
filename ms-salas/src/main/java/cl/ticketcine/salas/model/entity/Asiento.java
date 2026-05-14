package cl.ticketcine.salas.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "asientos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Asiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "id_asiento", nullable = false)
    private Integer idAsiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sala", nullable = false)
    private Sala sala;

    @Column(name = "fila", length = 1, nullable = false)
    private String fila;

    @Column(name = "numero", nullable = false)
    private Integer numero;
}
