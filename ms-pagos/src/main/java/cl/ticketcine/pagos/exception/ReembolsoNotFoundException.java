package cl.ticketcine.pagos.exception;

public class ReembolsoNotFoundException extends RuntimeException {

	public ReembolsoNotFoundException(Long id) {
		super("No se encontró ningún reembolso con id: " + id);
	}
}
