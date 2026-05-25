package cl.ticketcine.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuarios_proyeccion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuariosProyeccion {

    @Id
    @Column(name = "email", length = 100, nullable = false)
    private String email;

    @Column(name = "es_estudiante")
    private Boolean esEstudiante;
}
