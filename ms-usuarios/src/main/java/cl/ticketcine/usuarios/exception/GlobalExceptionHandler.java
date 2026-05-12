package cl.ticketcine.usuarios.exception;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Manejador global de excepciones para toda la API REST.
 *
 * @RestControllerAdvice intercepta todas las excepciones no capturadas en los
 * controladores y las transforma en respuestas HTTP coherentes con el envelope
 * ApiError. Esto desacopla la lógica de manejo de errores de los controladores,
 * centralizando la responsabilidad en un único punto (principio DRY + SRP).
 *
 * Orden de prioridad de los handlers: Spring usa el más específico primero.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // ─── 404 NOT FOUND ────────────────────────────────────────────────────────

    /**
     * Captura búsquedas de usuarios inexistentes (por email).
     * Devuelve 404 NOT FOUND con el mensaje de la excepción de dominio.
     */
    @ExceptionHandler(UsuarioNotFoundException.class)
    public ResponseEntity<ApiError> handleUsuarioNotFound(
            UsuarioNotFoundException ex,
            HttpServletRequest request) {

        ApiError error = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    /**
     * Captura búsquedas de membresias inexistentes.
     * Devuelve 404 NOT FOUND con el mensaje de la excepción de dominio.
     */
    @ExceptionHandler(MembresiaNotFoundException.class)
    public ResponseEntity<ApiError> handleMembresiaNotFound(
            MembresiaNotFoundException ex,
            HttpServletRequest request) {

        ApiError error = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    /**
     * Captura búsquedas de perfiles inexistentes.
     * Devuelve 404 NOT FOUND con el mensaje de la excepción de dominio.
     */
    @ExceptionHandler(PerfilNotFoundException.class)
    public ResponseEntity<ApiError> handlePerfilNotFound(
            PerfilNotFoundException ex,
            HttpServletRequest request) {

        ApiError error = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    /**
     * Captura intentos de crear usuarios con email duplicado.
     * Devuelve 409 CONFLICT con el mensaje de la excepción de dominio.
     */
    @ExceptionHandler(EmailDuplicadoException.class)
    public ResponseEntity<ApiError> handleEmailDuplicado(
            EmailDuplicadoException ex,
            HttpServletRequest request) {

        ApiError error = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT.value())
                .error(HttpStatus.CONFLICT.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    /**
     * Captura errores de validación de campos (@Valid en controladores).
     * Devuelve 400 BAD REQUEST con lista de errores de campo.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationErrors(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());

        ApiError error = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message("Errores de validación en los campos de entrada")
                .errors(errors)
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    /**
     * Captura errores de formato JSON malformado.
     * Devuelve 400 BAD REQUEST con mensaje genérico.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleJsonParseErrors(
            HttpMessageNotReadableException ex,
            HttpServletRequest request) {

        ApiError error = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message("El cuerpo de la petición JSON no es válido o está malformado")
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    // ─── 500 INTERNAL SERVER ERROR ───────────────────────────────────────────

    /**
     * Captura cualquier excepción no manejada específicamente.
     * Devuelve 500 INTERNAL SERVER ERROR con mensaje genérico (no exponer detalles internos).
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericErrors(
            Exception ex,
            HttpServletRequest request) {

        ApiError error = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .message("Ha ocurrido un error interno en el servidor")
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}