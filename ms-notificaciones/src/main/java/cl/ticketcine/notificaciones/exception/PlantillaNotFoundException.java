package cl.ticketcine.notificaciones.exception;

public class PlantillaNotFoundException extends RuntimeException {

    public PlantillaNotFoundException(String id) {
        super("Plantilla no encontrada con ID: " + id);
    }
}
