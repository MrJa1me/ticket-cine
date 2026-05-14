package cl.ticketcine.busqueda.dto;

import lombok.Data;

@Data
public class CategoriaResponse {

    private Integer idCat;
    private String nombre;
    private long cantidadPeliculas;
}
