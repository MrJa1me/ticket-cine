package com.microservicio.usuarios.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.Objects;

@Entity
@Table(name = "perfiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_perfil", nullable = false)
    private Integer idPerfil;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_email")
    private Usuario usuario;

    @Column(name = "preferencia_idioma", length = 10)
    private String preferenciaIdioma;

    @Column(name = "es_estudiante")
    private Boolean esEstudiante;

    @Column(name = "biografia", length = 255)
    private String biografia;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Perfil perfil = (Perfil) o;
        return Objects.equals(idPerfil, perfil.idPerfil);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPerfil);
    }
}
