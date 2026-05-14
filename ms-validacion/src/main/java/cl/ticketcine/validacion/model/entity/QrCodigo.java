package cl.ticketcine.validacion.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "qr_codigos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class QrCodigo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "id_qr", nullable = false)
    private Long idQr;

    @Column(name = "reserva_id")
    private UUID reservaId;

    @Lob
    @Column(name = "hash_code")
    private String hashCode;

    @Column(name = "activo")
    private Boolean activo;

    @OneToMany(mappedBy = "qrCodigo", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<HistorialAcceso> historialAccesos = new ArrayList<>();
}
