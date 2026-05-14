package cl.ticketcine.busqueda.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "peliculas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Pelicula {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "slug", nullable = false, length = 100)
    private String slug;

    @Column(name = "titulo", length = 150)
    private String titulo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cat", referencedColumnName = "id_cat")
    private Categoria categoria;

    @Column(name = "duracion_min")
    private Integer duracionMin;

    @Column(name = "estreno_anio")
    private Integer estrenoAnio;

    @OneToMany(mappedBy = "pelicula", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Elenco> elenco = new ArrayList<>();
}
