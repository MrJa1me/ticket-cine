package cl.ticketcine.busqueda.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuarios_proyeccion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UsuarioProyeccion {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "es_estudiante")
    private Boolean esEstudiante;
}
