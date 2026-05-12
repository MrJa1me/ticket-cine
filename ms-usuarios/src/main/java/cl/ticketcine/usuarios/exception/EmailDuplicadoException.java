package cl.ticketcine.usuarios.exception;

/**
 * Excepción de dominio lanzada cuando se intenta crear un usuario con un email ya existente.
 */
public class EmailDuplicadoException extends RuntimeException {

    public EmailDuplicadoException(String email) {
        super("Ya existe un usuario con el email: " + email);
    }
}