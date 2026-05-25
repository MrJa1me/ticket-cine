package cl.ticketcine.exception;

public class AplicacionPromoNotFoundException extends RuntimeException {

    public AplicacionPromoNotFoundException(Integer id) {
        super("Aplicación de promoción no encontrada con ID: " + id);
    }
}
