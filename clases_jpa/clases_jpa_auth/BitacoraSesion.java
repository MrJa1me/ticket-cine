package com.example.auth.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Objects;

@Entity
@Table(name = "bitacora_sesiones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BitacoraSesion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sesion")
    private Integer idSesion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_token", referencedColumnName = "id_token")
    private Token token;

    @Column(name = "ip_origen", length = 45)
    private String ipOrigen;

    @Column(name = "dispositivo", length = 50)
    private String dispositivo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BitacoraSesion that = (BitacoraSesion) o;
        return Objects.equals(idSesion, that.idSesion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idSesion);
    }
}
