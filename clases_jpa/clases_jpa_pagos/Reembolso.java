package com.microservicio.pagos.entities;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "reembolsos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reembolso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reem", nullable = false)
    private Long idReem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tx")
    private Transaccion transaccion;

    @Column(name = "motivo", length = 100)
    private String motivo;

    @Column(name = "monto_devuelto", precision = 10, scale = 2)
    private BigDecimal montoDevuelto;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reembolso reembolso = (Reembolso) o;
        return Objects.equals(idReem, reembolso.idReem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idReem);
    }
}
