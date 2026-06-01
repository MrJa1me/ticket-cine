package cl.ticketcine.reserva.exception;

/**
 * Excepción de dominio lanzada cuando un Asiento no es encontrado por ID.
 *
 * Extiende RuntimeException (unchecked) para no contaminar las firmas de los
 * métodos de servicio con throws, siguiendo la convención de Spring Framework.
 */
public class AsientoNotFoundException extends RuntimeException {

    /**
     * @param id identificador del asiento por el que se buscó
     */
    public AsientoNotFoundException(Integer id) {
        super("No se encontró ningún asiento con ID: " + id);
    }

    /**
     * @param message mensaje personalizado
     */
    public AsientoNotFoundException(String message) {
        super(message);
    }
}
