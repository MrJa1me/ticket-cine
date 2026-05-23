package cl.ticketcine.validacion.exception;

import cl.ticketcine.common.exception.EntityNotFoundException;

public class ValidacionNotFoundException extends EntityNotFoundException {

    public ValidacionNotFoundException(String message) {
        super("Recurso", "validacion", message);
    }
}
