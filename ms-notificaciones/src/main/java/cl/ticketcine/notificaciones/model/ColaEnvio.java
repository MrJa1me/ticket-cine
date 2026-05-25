package cl.ticketcine.notificaciones.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
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

    @Builder.Default
    @OneToMany(mappedBy = "notificacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LogError> logs = new ArrayList<>();

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
