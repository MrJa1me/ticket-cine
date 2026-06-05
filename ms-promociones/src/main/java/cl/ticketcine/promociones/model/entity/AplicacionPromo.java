package cl.ticketcine.promociones.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "aplicaciones_promo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AplicacionPromo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_app")
    private Long idApp;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "codigo_cupon", referencedColumnName = "codigo")
    private Cupon cupon;

    @Column(name = "user_email", nullable = false, length = 100)
    private String userEmail;

    @Column(name = "fecha_uso", nullable = false)
    private LocalDateTime fechaUso;

    @PrePersist
    private void prePersist() {
        if (fechaUso == null) {
            fechaUso = LocalDateTime.now();
        }
    }
}
