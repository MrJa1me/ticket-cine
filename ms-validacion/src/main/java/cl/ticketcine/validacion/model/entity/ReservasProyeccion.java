package cl.ticketcine.validacion.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "reservas_proyeccion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ReservasProyeccion {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "id_reserva", nullable = false)
    private UUID idReserva;

    @Column(name = "user_email", length = 100)
    private String userEmail;
}
