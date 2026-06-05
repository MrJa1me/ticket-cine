package cl.ticketcine.promociones.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AplicacionPromoResponse {

    private Long idApp;
    private String codigoCupon;
    private String userEmail;
    private LocalDateTime fechaUso;
}
