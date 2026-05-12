package cl.triskeledu.catalogo.exception;

public class LibroConTransaccionesException extends RuntimeException {

    public LibroConTransaccionesException(long id, String dependencias) {
        super("No se puede eliminar el libro con id " + id + ", porque tiene registros en: " + dependencias);
    }

    public LibroConTransaccionesException(String message) {
        super(message);
    }
}
