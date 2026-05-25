package cl.ticketcine.exception;

public class CampaniaNotFoundException extends RuntimeException {

    public CampaniaNotFoundException(Integer id) {
        super("Campaña no encontrada con ID: " + id);
    }
}
