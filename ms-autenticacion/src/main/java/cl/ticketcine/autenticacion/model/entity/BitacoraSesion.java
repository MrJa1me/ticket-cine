package cl.ticketcine.autenticacion.model.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entidad que mapea la tabla "bitacora_sesiones" en auth_db.
 * Registra metadata de cada inicio de sesion (IP, dispositivo).
 */
@Entity
@Table(name = "bitacora_sesiones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class BitacoraSesion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "id_sesion")
    private Integer idSesion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_token", referencedColumnName = "id_token")
    private Token token;

    @Column(name = "ip_origen", length = 45)
    private String ipOrigen;

    @Column(name = "dispositivo", length = 50)
    private String dispositivo;
}
