package cl.ticketcine.exception;

public class DuplicateResourceException extends RuntimeException {

    public DuplicateResourceException(String resource, String identifier) {
        super("El recurso " + resource + " ya existe: " + identifier);
    }
}
