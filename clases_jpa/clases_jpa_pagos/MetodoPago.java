package com.microservicio.pagos.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "metodos_pago")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MetodoPago {

    @Id
    @Column(name = "id_metodo", length = 10, nullable = false)
    private String idMetodo;

    @Column(name = "nombre", length = 50)
    private String nombre;

    @OneToMany(mappedBy = "metodoPago", cascade = CascadeType.ALL)
    private List<Transaccion> transacciones;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MetodoPago that = (MetodoPago) o;
        return Objects.equals(idMetodo, that.idMetodo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMetodo);
    }
}
