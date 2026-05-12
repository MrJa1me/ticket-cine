package com.example.salas.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "mantenimiento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mantenimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_maint", nullable = false)
    private Integer idMaint;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sala", nullable = false)
    private Sala sala;

    @Column(name = "ultima_fecha")
    private LocalDate ultimaFecha;

    @Column(name = "tecnico_nombre", length = 100)
    private String tecnicoNombre;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mantenimiento that = (Mantenimiento) o;
        return Objects.equals(idMaint, that.idMaint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMaint);
    }
}
