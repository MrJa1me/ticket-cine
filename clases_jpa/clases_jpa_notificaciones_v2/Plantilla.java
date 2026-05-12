package com.microservicio.notificaciones.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "plantillas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Plantilla {

    @Id
    @Column(name = "id_plantilla", length = 20, nullable = false)
    private String idPlantilla;

    @Column(name = "asunto", length = 100)
    private String asunto;

    @Lob
    @Column(name = "contenido")
    private String contenido;

    @OneToMany(mappedBy = "plantilla", cascade = CascadeType.ALL)
    private List<ColaEnvio> colaEnvios;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plantilla that = (Plantilla) o;
        return Objects.equals(idPlantilla, that.idPlantilla);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPlantilla);
    }
}
