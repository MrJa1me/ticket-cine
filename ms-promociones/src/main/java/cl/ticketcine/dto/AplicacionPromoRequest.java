package cl.ticketcine.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AplicacionPromoRequest {

    @NotBlank(message = "El código del cupón es obligatorio")
    @Size(max = 20, message = "El código del cupón no puede superar los 20 caracteres")
    private String codigoCupon;

    @NotBlank(message = "El email del usuario es obligatorio")
    @Email(message = "El email debe tener un formato válido")
    @Size(max = 100, message = "El email no puede superar los 100 caracteres")
    private String userEmail;
}