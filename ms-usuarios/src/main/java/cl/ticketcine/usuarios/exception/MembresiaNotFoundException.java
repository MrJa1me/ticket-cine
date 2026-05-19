package cl.ticketcine.usuarios.exception;

public class MembresiaNotFoundException extends RuntimeException {

    public MembresiaNotFoundException(Integer id) {
        super("Membresía no encontrada con ID: " + id);
    }

    public MembresiaNotFoundException(String usuarioEmail, String contexto) {
        super("Membresía no encontrada para el usuario: " + usuarioEmail + " (" + contexto + ")");
    }
}
