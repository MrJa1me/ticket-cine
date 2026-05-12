package com.microservicio.notificaciones.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.Objects;

@Entity
@Table(name = "logs_error")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogError {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_log", nullable = false)
    private Long idLog;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_notif")
    private ColaEnvio notificacion;

    @Column(name = "error_msg", length = 255)
    private String errorMsg;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogError logError = (LogError) o;
        return Objects.equals(idLog, logError.idLog);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idLog);
    }
}
