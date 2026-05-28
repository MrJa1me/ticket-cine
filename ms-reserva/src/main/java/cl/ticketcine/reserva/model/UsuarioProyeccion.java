package cl.ticketcine.reserva.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuarios_proyeccion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioProyeccion {

    @Id
    @Column(name = "email", nullable = false, length = 200)
    private String email;

    @Column(name = "nombre", nullable = false, length = 255)
    private String nombre;
}