package cl.ticketcine.notificaciones.exception;

public class ColaEnvioNotFoundException extends RuntimeException {

    public ColaEnvioNotFoundException(Long id) {
        super("Notificación no encontrada con ID: " + id);
    }
}
