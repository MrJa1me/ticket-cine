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
@Table(name = "reservas")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reserva {

    @Id
    @Column(name = "id_reserva", nullable = false)
    private UUID idReserva;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_email")
    private UsuarioProyeccion usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_fun")
    private FuncionProyeccion funcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cod_estado")
    private EstadoReserva estado;

    @CreatedDate
    @Column(name = "creado_at", nullable = false, updatable = false)
    private LocalDateTime creadoAt;

    @OneToMany(mappedBy = "reserva", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Ticket> tickets;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reserva reserva = (Reserva) o;
        return Objects.equals(idReserva, reserva.idReserva);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idReserva);
    }
}
