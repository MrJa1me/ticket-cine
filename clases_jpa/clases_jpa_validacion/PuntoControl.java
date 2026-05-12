package com.microservicio.validacion.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "puntos_control")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PuntoControl {

    @Id
    @Column(name = "id_punto", length = 10, nullable = false)
    private String idPunto;

    @Column(name = "ubicacion", length = 50)
    private String ubicacion;

    @OneToMany(mappedBy = "puntoControl", cascade = CascadeType.ALL)
    private List<HistorialAcceso> historialAccesos;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PuntoControl that = (PuntoControl) o;
        return Objects.equals(idPunto, that.idPunto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPunto);
    }
}
