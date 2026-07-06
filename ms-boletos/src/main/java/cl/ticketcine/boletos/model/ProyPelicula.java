package cl.ticketcine.boletos.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "proy_Peliculas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProyPelicula {

    @Id
    @Column(name = "id_Pelicula", nullable = false)
    private Integer idPelicula;

    @Column(name = "nombre_Pelicula", length = 100)
    private String nombrePelicula;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProyPelicula that = (ProyPelicula) o;
        return idPelicula != null && idPelicula.equals(that.idPelicula);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}