package cl.ticketcine.common.exception;

/**
 * Credenciales de autenticación incorrectas (correo inexistente o contraseña inválida).
 */
public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException(String message) {
        super(message);
    }
}
