package cl.ticketcine.notificaciones.exception;

public class LogErrorNotFoundException extends RuntimeException {

    public LogErrorNotFoundException(Long id) {
        super("Log de error no encontrado con ID: " + id);
    }
}
