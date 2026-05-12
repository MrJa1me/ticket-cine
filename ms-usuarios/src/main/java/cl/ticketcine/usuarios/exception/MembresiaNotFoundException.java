package cl.ticketcine.usuarios.exception;

/**
 * Excepción de dominio lanzada cuando una Membresia no es encontrada.
 */
public class MembresiaNotFoundException extends RuntimeException {

    public MembresiaNotFoundException(Integer id) {
        super("No se encontró ninguna membresía con ID: " + id);
    }

    public MembresiaNotFoundException(String usuarioEmail, String nivel) {
        super("No se encontró membresía para el usuario " + usuarioEmail + " con nivel " + nivel);
    }
}