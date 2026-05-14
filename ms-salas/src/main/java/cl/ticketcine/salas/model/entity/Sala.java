package cl.ticketcine.salas.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "salas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Sala {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "id_sala", length = 10, nullable = false)
    private String idSala;

    @Column(name = "formato", length = 10, nullable = false)
    private String formato;

    @Column(name = "capacidad", nullable = false)
    private Integer capacidad;

    @OneToMany(mappedBy = "sala", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Asiento> asientos = new ArrayList<>();

    @OneToMany(mappedBy = "sala", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Mantenimiento> mantenimientos = new ArrayList<>();
}
