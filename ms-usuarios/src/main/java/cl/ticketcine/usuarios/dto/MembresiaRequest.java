package cl.ticketcine.usuarios.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MembresiaRequest {

    @NotBlank(message = "El email del usuario es obligatorio")
    private String usuarioEmail;

    @NotBlank(message = "El nivel de membresia es obligatorio")
    @Size(max = 20, message = "El nivel no puede superar los 20 caracteres")
    private String nivel;

    private Integer puntosAcumulados;

    private LocalDate fechaVencimiento;
}