package cl.ticketcine.salas.exception;

public class SalaNotFoundException extends RuntimeException {

    public SalaNotFoundException(String idSala) {
        super("Sala no encontrada con id: " + idSala);
    }
}
