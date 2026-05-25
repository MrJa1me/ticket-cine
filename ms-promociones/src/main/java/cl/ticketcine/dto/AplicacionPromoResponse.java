package cl.ticketcine.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AplicacionPromoResponse {

    private Integer idApp;
    private String codigoCupon;
    private String userEmail;
    private LocalDateTime fechaUso;
}