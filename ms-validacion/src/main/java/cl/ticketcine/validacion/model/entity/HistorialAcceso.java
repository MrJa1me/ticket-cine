package cl.ticketcine.validacion.model.entity;

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
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class HistorialAcceso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
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
}
