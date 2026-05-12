package com.microservicio.validacion.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "historial_accesos")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistorialAcceso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_acc", nullable = false)
    private Long idAcc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_qr")
    private QrCodigo qrCodigo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_punto")
    private PuntoControl puntoControl;

    @CreatedDate
    @Column(name = "timestamp_ingreso", nullable = false, updatable = false)
    private LocalDateTime timestampIngreso;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HistorialAcceso that = (HistorialAcceso) o;
        return Objects.equals(idAcc, that.idAcc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAcc);
    }
}
