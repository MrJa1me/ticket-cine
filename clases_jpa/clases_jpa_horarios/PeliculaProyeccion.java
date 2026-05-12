package com.example.horarios.model.entity;


import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "peliculas_proyeccion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PeliculaProyeccion {

    @Id
    @Column(name = "slug", length = 100, nullable = false)
    private String slug;

    @Column(name = "titulo", length = 150)
    private String titulo;

    @OneToMany(mappedBy = "pelicula", cascade = CascadeType.ALL)
    private List<Funcion> funciones;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PeliculaProyeccion that = (PeliculaProyeccion) o;
        return Objects.equals(slug, that.slug);
    }

    @Override
    public int hashCode() {
        return Objects.hash(slug);
    }
}
