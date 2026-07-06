package cl.ticketcine.catalogo.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "Peliculas")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Pelicula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Pelicula", nullable = false)
    private Integer idPelicula;

    @Column(name = "nombre_Pelicula", length = 100)
    private String nombrePelicula;

    @Column(name = "categoria", length = 50)
    private String categoria;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "estado", length = 20)
    private String estado;

    @Column(name = "idioma", length = 30)
    private String idioma;

    @Column(name = "duracion_minutos")
    private Integer duracionMinutos;

    @Column(name = "clasificacion", length = 10)
    private String clasificacion;

    @Column(name = "sinopsis", columnDefinition = "TEXT")
    private String sinopsis;

    @Column(name = "id_genero")
    private Integer idGenero;
}
