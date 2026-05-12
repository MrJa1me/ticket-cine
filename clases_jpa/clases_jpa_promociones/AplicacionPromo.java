package com.microservicio.promociones.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "aplicaciones_promo")
@EntityListeners(AuditingEntityListener.class)
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codigo_cupon")
    private Cupon cupon;

    @Column(name = "user_email", length = 100)
    private String userEmail;

    @CreatedDate
    @Column(name = "fecha_uso", nullable = false, updatable = false)
    private LocalDateTime fechaUso;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AplicacionPromo that = (AplicacionPromo) o;
        return Objects.equals(idApp, that.idApp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idApp);
    }
}
