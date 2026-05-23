package cl.ticketcine.common.exception;

/**
 * Excepción lanzada cuando se intenta crear un recurso que ya existe.
 */
public class DuplicateResourceException extends RuntimeException {

    public DuplicateResourceException(String message) {
        super(message);
    }

    public DuplicateResourceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor recomendado. Sigue el patrón del profesor:
     * new DuplicateResourceException("Un Usuario", "email", email, nombre + " " + apellido) →
     *   "Un Usuario con email igual a 'test@mail.com' ya existe en el sistema, descrito por 'Nombre Apellido'."
     */
    public DuplicateResourceException(String entity, String field, Object value, String description) {
        super(String.format("%s con %s igual a '%s' ya existe en el sistema, descrito por '%s'.",
                entity, field, (value != null ? value.toString() : "N/A"), description));
    }
}