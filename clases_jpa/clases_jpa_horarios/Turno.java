package com.example.horarios.model.entity;


import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "turnos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Turno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_turno", nullable = false)
    private Integer idTurno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_fun")
    private Funcion funcion;

    @Column(name = "hora_inicio")
    private LocalTime horaInicio;

    @Column(name = "es_prime")
    private Boolean esPrime;

    @OneToMany(mappedBy = "turno", cascade = CascadeType.ALL)
    private List<PrecioHorario> precios;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Turno turno = (Turno) o;
        return Objects.equals(idTurno, turno.idTurno);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTurno);
    }
}
