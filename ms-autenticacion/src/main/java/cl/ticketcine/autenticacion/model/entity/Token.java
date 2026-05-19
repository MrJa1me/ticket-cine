package cl.ticketcine.autenticacion.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad que mapea la tabla "tokens" en auth_db.
 * Almacena tokens de sesion generados durante la autenticacion.
 */
@Entity
@Table(name = "tokens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "id_token")
    private Integer idToken;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_email", referencedColumnName = "user_email")
    private Credencial credencial;

    @Column(name = "jwt_secret", columnDefinition = "TEXT")
    private String jwtSecret;

    @Column(name = "expira_at")
    private LocalDateTime expiraAt;

    @OneToMany(mappedBy = "token", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<BitacoraSesion> sesiones = new ArrayList<>();
}
