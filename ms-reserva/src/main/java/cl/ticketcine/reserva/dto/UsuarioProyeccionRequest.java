package cl.ticketcine.reserva.dto;


import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UsuarioProyeccionRequest {

    @NotBlank(message = "El SKU es obligatorio")
    @Size(max = 50, message = "El SKU no puede superar los 50 caracteres")
    private String sku;

    @NotBlank(message = "El tipo de recurso es obligatorio")
    @Pattern(
        regexp = "Libro|Notebook|Tablet|Juego de mesa",
        message = "El tipo de recurso debe ser: Libro, Notebook, Tablet o Juego de mesa"
    )
    private String tipoRecurso;

    // EMAIL solo obligatorio cuando tipoRecurso = 'email'; se valida en el service.
    @Size(max = 20, message = "El EMAIL no puede superar los 20 caracteres")
    private String email;

    @Pattern(
        regexp = "Excelente|Buen estado|Dañado|En reparación",
        message = "El estado físico debe ser: Excelente, Buen estado, Dañado o En reparación"
    )
    private String estadoFisico;

    private Boolean disponible;
}

