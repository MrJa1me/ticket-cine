package cl.ticketcine.horarios.model;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "funciones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Funcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_fun", nullable = false)
    private Integer idFun;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "peli_slug")
    private PeliculaProyeccion pelicula;

    @Column(name = "sala_id", length = 10)
    private String salaId;

    @Column(name = "fecha_dia")
    private LocalDate fechaDia;

    @OneToMany(mappedBy = "funcion", cascade = CascadeType.ALL)
    private List<Turno> turnos;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Funcion funcion = (Funcion) o;
        return Objects.equals(idFun, funcion.idFun);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idFun);
    }
}
