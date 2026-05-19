package cl.ticketcine.common.exception;

/**
 * Excepción lanzada cuando una entidad no es encontrada en la base de datos.
 */
public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor recomendado. Sigue el patrón del profesor:
     * new EntityNotFoundException("Usuarios", "ID", id) →
     *   "No se encontraron registros en Usuarios con el ID '123'"
     */
    public EntityNotFoundException(String entity, String key, Object value) {
        super(String.format("No se encontraron registros en %s con el %s '%s'",
                entity, key, (value != null ? value.toString() : "N/A")));
    }
}