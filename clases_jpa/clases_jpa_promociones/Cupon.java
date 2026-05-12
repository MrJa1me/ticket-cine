package com.microservicio.promociones.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.util.Objects;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_camp")
    private Campania campania;

    @Column(name = "pct_desc")
    private Integer pctDesc;

    @Column(name = "activo")
    private Boolean activo;

    @OneToMany(mappedBy = "cupon", cascade = CascadeType.ALL)
    private List<AplicacionPromo> aplicaciones;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cupon cupon = (Cupon) o;
        return Objects.equals(codigo, cupon.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }
}
