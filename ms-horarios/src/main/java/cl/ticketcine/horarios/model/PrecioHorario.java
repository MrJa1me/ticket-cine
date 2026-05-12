package cl.ticketcine.horarios.model;


import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "precios_horario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrecioHorario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_precio", nullable = false)
    private Integer idPrecio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_turno")
    private Turno turno;

    @Column(name = "monto_base", precision = 10, scale = 2)
    private BigDecimal montoBase;

    @Column(name = "moneda", length = 3)
    private String moneda;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrecioHorario that = (PrecioHorario) o;
        return Objects.equals(idPrecio, that.idPrecio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPrecio);
    }
}
