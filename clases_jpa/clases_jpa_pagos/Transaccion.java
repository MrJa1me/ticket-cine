package com.microservicio.pagos.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.Objects;

@Entity
@Table(name = "transacciones")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tx", nullable = false)
    private Long idTx;

    @Column(name = "reserva_id")
    private UUID reservaId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "metodo_id")
    private MetodoPago metodoPago;

    @Column(name = "monto", precision = 10, scale = 2)
    private BigDecimal monto;

    @CreatedDate
    @Column(name = "fecha_tx", nullable = false, updatable = false)
    private LocalDateTime fechaTx;

    @OneToMany(mappedBy = "transaccion", cascade = CascadeType.ALL)
    private List<Reembolso> reembolsos;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaccion that = (Transaccion) o;
        return Objects.equals(idTx, that.idTx);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTx);
    }
}
