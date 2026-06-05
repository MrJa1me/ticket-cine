package cl.ticketcine.promociones.model.entity;

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
    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "es_estudiante")
    private Boolean esEstudiante;
}
