package cl.ticketcine.notificaciones.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LogErrorRequest {

    @NotNull(message = "El id de la notificación es obligatorio")
    private Long idNotif;

    @NotBlank(message = "El mensaje de error es obligatorio")
    @Size(max = 255, message = "El mensaje de error no puede superar los 255 caracteres")
    private String errorMsg;
}
