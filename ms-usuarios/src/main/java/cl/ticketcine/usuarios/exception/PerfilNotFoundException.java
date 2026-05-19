package cl.ticketcine.usuarios.exception;

public class PerfilNotFoundException extends RuntimeException {

    public PerfilNotFoundException(Integer id) {
        super("Perfil no encontrado con ID: " + id);
    }

    public PerfilNotFoundException(String usuarioEmail) {
        super("Perfil no encontrado para el usuario: " + usuarioEmail);
    }
}
