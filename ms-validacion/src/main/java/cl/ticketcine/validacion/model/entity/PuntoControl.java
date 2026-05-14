package cl.ticketcine.validacion.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "puntos_control")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PuntoControl {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "id_punto", length = 10, nullable = false)
    private String idPunto;

    @Column(name = "ubicacion", length = 50, nullable = false)
    private String ubicacion;

    @OneToMany(mappedBy = "puntoControl", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<HistorialAcceso> historialAccesos = new ArrayList<>();
}
