package cl.ticketcine.exception;

public class UsuariosProyeccionNotFoundException extends RuntimeException {

    public UsuariosProyeccionNotFoundException(String email) {
        super("Usuario de proyección no encontrado: " + email);
    }
}
