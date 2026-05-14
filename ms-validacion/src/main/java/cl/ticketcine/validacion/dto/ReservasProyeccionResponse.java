package cl.ticketcine.validacion.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ReservasProyeccionResponse {

    private UUID idReserva;
    private String userEmail;
}
