package cl.ticketcine.autenticacion.model.entity;

import jakarta.persistence.*;
import lombok.*;

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
