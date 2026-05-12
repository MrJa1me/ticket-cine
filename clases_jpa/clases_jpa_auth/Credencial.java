package com.example.auth.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "credenciales")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Credencial {

    @Id
    @Column(name = "user_email", nullable = false, length = 100)
    private String userEmail;

    @Column(name = "pass_hash", length = 255)
    private String passHash;

    @Column(name = "mfa_habilitado")
    private Boolean mfaHabilitado;

    @OneToMany(mappedBy = "credencial", cascade = CascadeType.ALL)
    private List<Token> tokens;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Credencial that = (Credencial) o;
        return Objects.equals(userEmail, that.userEmail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userEmail);
    }
}
