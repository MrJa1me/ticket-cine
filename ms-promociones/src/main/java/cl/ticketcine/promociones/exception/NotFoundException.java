package cl.ticketcine.promociones.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String resource, Object key) {
        super(String.format("%s con identificador %s no encontrado.", resource, key));
    }
}
