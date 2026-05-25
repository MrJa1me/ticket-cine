package cl.ticketcine.pagos.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class PagosResponse {

    private Long idTx;
    private UUID reservaId;
    private String metodoId;
    private BigDecimal monto;
    private LocalDateTime fechaTx;
}
