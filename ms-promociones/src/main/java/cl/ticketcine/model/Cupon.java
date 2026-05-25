package cl.ticketcine.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "cupones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cupon {

    @Id
    @Column(name = "codigo", length = 20, nullable = false)
    private String codigo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_camp", nullable = false)
    private Campania campania;

    @Column(name = "pct_desc")
    private Integer pctDesc;

    @Column(name = "activo")
    private Boolean activo;

    @OneToMany(mappedBy = "cupon", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AplicacionPromo> aplicaciones;
}
