package cl.ticketcine.usuarios.exception;

/**
 * Excepción de dominio lanzada cuando un Usuario no es encontrado por email.
 *
 * Extiende RuntimeException (unchecked) para no contaminar las firmas de los
 * métodos de servicio con throws, siguiendo la convención de Spring Framework
 * y microservicios modernos.
 */
public class UsuarioNotFoundException extends RuntimeException {

    /**
     * @param email identificador por el que se buscó el usuario
     */
    public UsuarioNotFoundException(String email) {
        super("No se encontró ningún usuario con email: " + email);
    }
}