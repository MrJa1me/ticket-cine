package cl.ticketcine.usuarios.exception;

/**
 * Excepción de dominio lanzada cuando un Perfil no es encontrado.
 */
public class PerfilNotFoundException extends RuntimeException {

    public PerfilNotFoundException(Integer id) {
        super("No se encontró ningún perfil con ID: " + id);
    }

    public PerfilNotFoundException(String usuarioEmail) {
        super("No se encontró perfil para el usuario: " + usuarioEmail);
    }
}