package cl.ticketcine.notificaciones.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogErrorResponse {

    private Long idLog;
    private Long idNotif;
    private String errorMsg;
}
