package cl.ticketcine.usuarios.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(
    name = "usuarios",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_usuarios_email", columnNames = "email")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Usuario {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "telefono", length = 20)
    private String telefono;

    @Column(name = "pais_codigo", length = 2)
    private String paisCodigo;

    @Column(name = "fecha_registro")
    private LocalDate fechaRegistro;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Perfil perfil;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Membresia membresia;
}