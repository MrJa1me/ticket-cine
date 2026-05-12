package com.microservicio.pagos.entities;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.UUID;
import java.util.Objects;

@Entity
@Table(name = "reservas_proyeccion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservasProyeccion {

    @Id
    @Column(name = "id_reserva", nullable = false)
    private UUID idReserva;

    @Column(name = "monto_total", precision = 10, scale = 2)
    private BigDecimal montoTotal;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservasProyeccion that = (ReservasProyeccion) o;
        return Objects.equals(idReserva, that.idReserva);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idReserva);
    }
}
