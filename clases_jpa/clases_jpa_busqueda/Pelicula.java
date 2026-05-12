package com.example.busqueda.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "peliculas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pelicula {

    @Id
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

    @OneToMany(mappedBy = "pelicula", cascade = CascadeType.ALL)
    private List<Elenco> elenco;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pelicula pelicula = (Pelicula) o;
        return Objects.equals(slug, pelicula.slug);
    }

    @Override
    public int hashCode() {
        return Objects.hash(slug);
    }
}
