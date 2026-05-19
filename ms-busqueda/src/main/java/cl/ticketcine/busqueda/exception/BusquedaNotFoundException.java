package cl.ticketcine.busqueda.exception;

import cl.ticketcine.common.exception.EntityNotFoundException;

public class BusquedaNotFoundException extends EntityNotFoundException {

    public BusquedaNotFoundException(String message) {
        super("Recurso", "busqueda", message);
    }
}
