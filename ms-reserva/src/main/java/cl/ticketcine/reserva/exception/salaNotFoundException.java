package cl.ticketcine.reserva.exception;

/**
 * Excepción de dominio lanzada cuando una Sala no es encontrada por ID.
 *
 * Extiende RuntimeException (unchecked) para no contaminar las firmas de los
 * métodos de servicio con throws, siguiendo la convención de Spring Framework.
 */
public class salaNotFoundException extends RuntimeException {

    /**
     * @param idSala identificador de la sala por la que se buscó
     */
    public salaNotFoundException(String idSala) {
        super("No se encontró ninguna sala con ID: " + idSala);
    }
}
