package cl.ticketcine.usuarios.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioResponse {

    private String email;
    private String nombre;
    private String telefono;
    private String paisCodigo;
    private LocalDate fechaRegistro;
}