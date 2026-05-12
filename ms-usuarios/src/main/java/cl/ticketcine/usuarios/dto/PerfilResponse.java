package cl.ticketcine.usuarios.dto;

import lombok.Data;

@Data
public class PerfilResponse {

    private Integer idPerfil;
    private String usuarioEmail;
    private String preferenciaIdioma;
    private Boolean esEstudiante;
    private String biografia;
}