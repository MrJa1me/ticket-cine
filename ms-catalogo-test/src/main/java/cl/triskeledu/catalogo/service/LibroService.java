package cl.triskeledu.catalogo.service;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import cl.triskeledu.catalogo.dto.LibroRequest;
import cl.triskeledu.catalogo.dto.LibroResponse;
import cl.triskeledu.catalogo.exception.IsbnDuplicadoException;
import cl.triskeledu.catalogo.exception.LibroConTransaccionesException;
import cl.triskeledu.catalogo.exception.LibroNotFoundException;
import cl.triskeledu.catalogo.mapper.LibroMapper;
import cl.triskeledu.catalogo.model.Libro;
import cl.triskeledu.catalogo.repository.LibroCategoriaRepository;
import cl.triskeledu.catalogo.repository.LibroRepository;

import lombok.RequiredArgsConstructor;

/**
 * Servicio encargado de la lógica de negocio relacionada con libros.
 * Gestiona operaciones CRUD, validaciones de negocio y reglas de integridad.
 */
@Service
@RequiredArgsConstructor
public class LibroService {

    private final LibroRepository libroRepository;
    private final LibroCategoriaRepository libroCategoriaRepository;
    private final LibroMapper libroMapper;

    public List<LibroResponse> findAll() {
        return libroMapper.toResponseList(libroRepository.findAll());
    }

    public LibroResponse findById(long id) {
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new LibroNotFoundException(id));
        return libroMapper.toResponse(libro);
    }

    public LibroResponse findByIsbn(String isbn) {
        Libro libro = libroRepository.findByIsbn(isbn).orElseThrow(() -> new LibroNotFoundException(isbn));
        return libroMapper.toResponse(libro);
    }

    public LibroResponse create(LibroRequest request) {

        if (libroRepository.existsByIsbn(request.getIsbn())) {
            String tituloExistente = libroRepository.findByIsbn(request.getIsbn())
                    .map(Libro::getTitulo)
                    .orElse("Desconocido");
            throw new IsbnDuplicadoException(request.getIsbn(), tituloExistente);
        }

        Libro libro = libroMapper.toModel(request);

        if (libro == null) {
            throw new IllegalArgumentException("La solicitud de libro no pudo ser procesada.");
        }

        Libro guardado = libroRepository.save(libro);
        return libroMapper.toResponse(guardado);
    }

    public LibroResponse update(long id, LibroRequest request) {

        Libro existente = libroRepository.findById(id)
                .orElseThrow(() -> new LibroNotFoundException(id));

        if (!existente.getIsbn().equalsIgnoreCase(request.getIsbn())) {
            if (libroRepository.existsByIsbn(request.getIsbn())) {
                libroRepository.findByIsbn(request.getIsbn()).ifPresent(libro -> {
                    throw new IsbnDuplicadoException(request.getIsbn(), libro.getTitulo());
                });
            }
        }

        existente.setIsbn(request.getIsbn());
        existente.setTitulo(request.getTitulo());
        existente.setEditorial(request.getEditorial());
        existente.setAnioPublicacion(request.getAnioPublicacion());
        existente.setAutor(request.getAutor());

        Libro guardado = libroRepository.save(existente);
        return libroMapper.toResponse(guardado);
    }

    public void deleteById(long id) {

        libroRepository.findById(id).orElseThrow(() -> new LibroNotFoundException(id));

        List<String> dependencias = new ArrayList<>();

        if (libroCategoriaRepository.existsByLibroId(id)) {
            dependencias.add("Categoría de libro (ej: el libro está asociado a una categoría)");
        }

        if (!dependencias.isEmpty()) {
            throw new LibroConTransaccionesException(id, String.join(", ", dependencias));
        }

        libroRepository.deleteById(id);
    }
}