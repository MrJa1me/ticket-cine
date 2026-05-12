package com.example.busqueda.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Objects;

@Entity
@Table(name = "elenco")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Elenco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_actor")
    private Integer idActor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "peli_slug", referencedColumnName = "slug")
    private Pelicula pelicula;

    @Column(name = "nombre_actor", length = 100)
    private String nombreActor;

    @Column(name = "papel", length = 50)
    private String papel;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Elenco elenco = (Elenco) o;
        return Objects.equals(idActor, elenco.idActor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idActor);
    }
}
