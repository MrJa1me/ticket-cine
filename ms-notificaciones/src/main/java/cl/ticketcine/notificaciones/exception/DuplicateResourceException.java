package cl.ticketcine.notificaciones.exception;

public class DuplicateResourceException extends RuntimeException {

    public DuplicateResourceException(String resource, String value) {
        super(resource + " ya existe: " + value);
    }
}
