package com.example.auth.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tokens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_token")
    private Integer idToken;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_email", referencedColumnName = "user_email")
    private Credencial credencial;

    @Lob
    @Column(name = "jwt_secret")
    private String jwtSecret;

    @Column(name = "expira_at")
    private LocalDateTime expiraAt;

    @OneToMany(mappedBy = "token", cascade = CascadeType.ALL)
    private List<BitacoraSesion> sesiones;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        return Objects.equals(idToken, token.idToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idToken);
    }
}
