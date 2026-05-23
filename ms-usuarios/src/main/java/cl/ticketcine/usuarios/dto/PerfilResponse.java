package cl.ticketcine.usuarios.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PerfilResponse {

    private Integer idPerfil;
    private String usuarioEmail;
    private String preferenciaIdioma;
    private Boolean esEstudiante;
    private String biografia;
}