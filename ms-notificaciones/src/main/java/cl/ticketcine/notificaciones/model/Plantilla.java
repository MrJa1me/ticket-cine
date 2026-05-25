package cl.ticketcine.notificaciones.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
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

    @Builder.Default
    @OneToMany(mappedBy = "plantilla", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ColaEnvio> colaEnvios = new ArrayList<>();

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
