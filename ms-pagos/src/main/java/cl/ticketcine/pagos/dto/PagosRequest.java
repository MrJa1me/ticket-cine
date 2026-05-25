package cl.ticketcine.pagos.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class PagosRequest {

    @NotNull(message = "El id de reserva es obligatorio")
    private UUID reservaId;

    @NotNull(message = "El id del método de pago es obligatorio")
    private String metodoId;

    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe ser positivo")
    private BigDecimal monto;
}
