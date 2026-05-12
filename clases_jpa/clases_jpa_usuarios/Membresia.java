package com.microservicio.usuarios.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "membresias")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Membresia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_membresia", nullable = false)
    private Integer idMembresia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_email")
    private Usuario usuario;

    @Column(name = "nivel", length = 20)
    private String nivel;

    @Column(name = "puntos_acumulados")
    private Integer puntosAcumulados;

    @Column(name = "fecha_vencimiento")
    private LocalDate fechaVencimiento;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Membresia membresia = (Membresia) o;
        return Objects.equals(idMembresia, membresia.idMembresia);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMembresia);
    }
}
