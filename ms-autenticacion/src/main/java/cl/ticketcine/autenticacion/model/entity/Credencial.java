package cl.ticketcine.autenticacion.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "credenciales")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Credencial {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "user_email", nullable = false, length = 100)
    private String userEmail;

    @Column(name = "pass_hash", length = 255, nullable = false)
    private String passHash;

    @Column(name = "mfa_habilitado")
    private Boolean mfaHabilitado;

    @Column(name = "roles", length = 255, nullable = false)
    private String roles;

    @OneToMany(mappedBy = "credencial", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Token> tokens = new ArrayList<>();

    public List<String> getRoleList() {
        return roles == null || roles.isBlank()
                ? List.of()
                : List.of(roles.split(","));
    }

    public void setRoleList(List<String> roles) {
        this.roles = String.join(",", roles);
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}
