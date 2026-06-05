package cl.ticketcine.promociones.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cupones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cupon {

    @Id
    @Column(name = "codigo", length = 20)
    private String codigo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_camp")
    private Campania campania;

    @Column(name = "pct_desc")
    private Integer pctDesc;

    @Column(name = "activo")
    private Boolean activo;
}
