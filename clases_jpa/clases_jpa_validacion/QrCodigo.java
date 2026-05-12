package com.microservicio.validacion.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.util.UUID;
import java.util.Objects;

@Entity
@Table(name = "qr_codigos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QrCodigo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_qr", nullable = false)
    private Long idQr;

    @Column(name = "reserva_id")
    private UUID reservaId;

    @Lob
    @Column(name = "hash_code")
    private String hashCode;

    @Column(name = "activo")
    private Boolean activo;

    @OneToMany(mappedBy = "qrCodigo", cascade = CascadeType.ALL)
    private List<HistorialAcceso> historialAccesos;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QrCodigo qrCodigo = (QrCodigo) o;
        return Objects.equals(idQr, qrCodigo.idQr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idQr);
    }
}
