package com.microservicio.notificaciones.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "cola_envios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ColaEnvio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_notif", nullable = false)
    private Long idNotif;

    @Column(name = "user_email", length = 100)
    private String userEmail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_plantilla")
    private Plantilla plantilla;

    @Column(name = "estado_envio", length = 10)
    private String estadoEnvio;

    @OneToMany(mappedBy = "notificacion", cascade = CascadeType.ALL)
    private List<LogError> logs;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ColaEnvio that = (ColaEnvio) o;
        return Objects.equals(idNotif, that.idNotif);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idNotif);
    }
}
