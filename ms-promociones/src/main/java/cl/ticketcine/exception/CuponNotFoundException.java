package cl.ticketcine.exception;

public class CuponNotFoundException extends RuntimeException {

    public CuponNotFoundException(String codigo) {
        super("Cupón no encontrado con código: " + codigo);
    }
}
