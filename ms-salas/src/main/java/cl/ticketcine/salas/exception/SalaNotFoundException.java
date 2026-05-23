package cl.ticketcine.salas.exception;

import cl.ticketcine.common.exception.EntityNotFoundException;

public class SalaNotFoundException extends EntityNotFoundException {

    public SalaNotFoundException(String idSala) {
        super("Sala", "id", idSala);
    }
}
