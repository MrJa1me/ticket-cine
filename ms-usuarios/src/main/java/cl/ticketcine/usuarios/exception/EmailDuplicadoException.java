package cl.ticketcine.usuarios.exception;

public class EmailDuplicadoException extends RuntimeException {

    public EmailDuplicadoException(String email) {
        super("El email ya está en uso: " + email);
    }
}
