package com.microservicio.promociones.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsuariosProyeccion that = (UsuariosProyeccion) o;
        return Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
