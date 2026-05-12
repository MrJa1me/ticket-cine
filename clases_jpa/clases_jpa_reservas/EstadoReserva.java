package com.example.reservas.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Entity
@Table(name = "estados_reserva")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstadoReserva {

    @Id
    @Column(name = "cod_estado", length = 10, nullable = false)
    private String codEstado;

    @Column(name = "descripcion", length = 50)
    private String descripcion;

    @OneToMany(mappedBy = "estado", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reserva> reservas;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EstadoReserva that = (EstadoReserva) o;
        return Objects.equals(codEstado, that.codEstado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codEstado);
    }
}
