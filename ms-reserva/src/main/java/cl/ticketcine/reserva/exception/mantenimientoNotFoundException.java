package cl.ticketcine.reserva.exception;

/**
 * Excepción de dominio lanzada cuando un Mantenimiento no es encontrado por ID.
 *
 * Extiende RuntimeException (unchecked) para no contaminar las firmas de los
 * métodos de servicio con throws, siguiendo la convención de Spring Framework.
 */
public class mantenimientoNotFoundException extends RuntimeException {

    /**
     * @param idMaint identificador del mantenimiento por el que se buscó
     */
    public mantenimientoNotFoundException(Integer idMaint) {
        super("No se encontró ningún mantenimiento con ID: " + idMaint);
    }

    /**
     * @param message mensaje personalizado
     */
    public mantenimientoNotFoundException(String message) {
        super(message);
    }
}
