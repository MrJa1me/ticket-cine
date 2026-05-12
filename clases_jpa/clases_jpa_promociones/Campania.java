package com.microservicio.promociones.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "campanias")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Campania {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_camp", nullable = false)
    private Integer idCamp;

    @Column(name = "nombre", length = 50)
    private String nombre;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @OneToMany(mappedBy = "campania", cascade = CascadeType.ALL)
    private List<Cupon> cupones;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Campania campania = (Campania) o;
        return Objects.equals(idCamp, campania.idCamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCamp);
    }
}
