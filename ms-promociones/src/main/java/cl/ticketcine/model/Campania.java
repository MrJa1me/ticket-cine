package cl.ticketcine.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "campanias")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Campania {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_camp", nullable = false)
    private Integer idCamp;

    @Column(name = "nombre", length = 50)
    private String nombre;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @OneToMany(mappedBy = "campania", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cupon> cupones;
}
