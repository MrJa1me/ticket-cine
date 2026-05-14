package cl.ticketcine.busqueda.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "elenco")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Elenco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "id_actor")
    private Integer idActor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "peli_slug", referencedColumnName = "slug")
    private Pelicula pelicula;

    @Column(name = "nombre_actor", length = 100)
    private String nombreActor;

    @Column(name = "papel", length = 50)
    private String papel;
}
