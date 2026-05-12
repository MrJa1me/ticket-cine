package cl.ticketcine.usuarios.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class UsuarioResponse {

    private String email;
    private String nombre;
    private String telefono;
    private String paisCodigo;
    private LocalDate fechaRegistro;
}