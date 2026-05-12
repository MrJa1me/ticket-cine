package com.microservicio.validacion.entities;

import jakarta.persistence.*;
import lombok.*;
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

    @Column(name = "user_email", length = 100)
    private String userEmail;

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
