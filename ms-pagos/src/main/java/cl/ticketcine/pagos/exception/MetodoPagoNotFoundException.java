package cl.ticketcine.pagos.exception;

/** Excepción lanzada cuando no se encuentra un método de pago por id. */
public class MetodoPagoNotFoundException extends RuntimeException {

	public MetodoPagoNotFoundException(String id) {
		super("No se encontró ningún método de pago con id: " + id);
	}
}
