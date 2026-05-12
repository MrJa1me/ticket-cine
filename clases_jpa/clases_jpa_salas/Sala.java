package com.example.salas.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "salas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sala {

    @Id
    @Column(name = "id_sala", length = 10, nullable = false)
    private String idSala;

    @Column(name = "formato", length = 10)
    private String formato;

    @Column(name = "capacidad")
    private Integer capacidad;

    @OneToMany(mappedBy = "sala", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Asiento> asientos;

    @OneToMany(mappedBy = "sala", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Mantenimiento> mantenimientos;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sala sala = (Sala) o;
        return Objects.equals(idSala, sala.idSala);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idSala);
    }
}
