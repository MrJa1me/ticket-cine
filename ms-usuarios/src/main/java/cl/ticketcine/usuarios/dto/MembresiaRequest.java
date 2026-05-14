package cl.ticketcine.usuarios.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import lombok.Data;
import java.time.LocalDate;

@Data
public class MembresiaRequest {

    @NotBlank(message = "El email del usuario es obligatorio")
    private String usuarioEmail;

    @NotBlank(message = "El nivel de membresía es obligatorio")
    private String nivel;

    @NotNull(message = "Los puntos acumulados son obligatorios")
    @Min(value = 0, message = "Los puntos no pueden ser negativos")
    private Integer puntosAcumulados;

    private LocalDate fechaVencimiento;
}