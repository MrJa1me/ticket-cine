package cl.ticketcine.promociones.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AplicacionPromoRequest {

    @NotBlank(message = "El código del cupón es obligatorio")
    private String codigoCupon;

    @NotBlank(message = "El email del usuario es obligatorio")
    private String userEmail;
}
