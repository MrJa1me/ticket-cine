package cl.ticketcine.busqueda.dto;

import lombok.Data;

@Data
public class PeliculaResponse {

    private String slug;
    private String titulo;
    private Integer idCat;
    private String categoriaNombre;
    private Integer duracionMin;
    private Integer estrenoAnio;
    private long cantidadActores;
}
