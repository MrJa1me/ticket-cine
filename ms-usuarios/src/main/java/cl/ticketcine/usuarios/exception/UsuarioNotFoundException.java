package cl.ticketcine.usuarios.exception;

public class UsuarioNotFoundException extends RuntimeException {

    public UsuarioNotFoundException(String email) {
        super("Usuario no encontrado: " + email);
    }
}
