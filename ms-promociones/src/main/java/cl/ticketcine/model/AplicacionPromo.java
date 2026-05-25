package cl.ticketcine.model;

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
    @Column(name = "id_app", nullable = false)
    private Integer idApp;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "codigo_cupon", nullable = false)
    private Cupon cupon;

    @Column(name = "user_email", length = 100, nullable = false)
    private String userEmail;

    @Column(name = "fecha_uso", nullable = false)
    private LocalDateTime fechaUso;
}
