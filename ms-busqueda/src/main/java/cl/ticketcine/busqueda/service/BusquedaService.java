package cl.ticketcine.busqueda.service;

import cl.ticketcine.busqueda.dto.CategoriaRequest;
import cl.ticketcine.busqueda.dto.CategoriaResponse;
import cl.ticketcine.busqueda.dto.PeliculaRequest;
import cl.ticketcine.busqueda.dto.PeliculaResponse;
import cl.ticketcine.busqueda.dto.UsuarioProyeccionResponse;
import cl.ticketcine.busqueda.exception.BusquedaNotFoundException;
import cl.ticketcine.busqueda.mapper.BusquedaMapper;
import cl.ticketcine.busqueda.model.entity.Categoria;
import cl.ticketcine.busqueda.model.entity.Pelicula;
import cl.ticketcine.busqueda.model.entity.UsuarioProyeccion;
import cl.ticketcine.busqueda.repository.CategoriaRepository;
import cl.ticketcine.busqueda.repository.PeliculaRepository;
import cl.ticketcine.busqueda.repository.UsuarioProyeccionRepository;
import cl.ticketcine.busqueda.event.BusquedaEventProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BusquedaService {

    private final CategoriaRepository categoriaRepository;
    private final PeliculaRepository peliculaRepository;
    private final UsuarioProyeccionRepository usuarioRepository;
    private final BusquedaMapper mapper;
    private final BusquedaEventProducer eventProducer;

    public List<CategoriaResponse> findAllCategorias() {
        return mapper.toCategoriaResponseList(categoriaRepository.findAll());
    }

    public CategoriaResponse findCategoriaById(Integer idCat) {
        Integer idCatNotNull = Objects.requireNonNull(idCat, "El ID de categoría es obligatorio");
        Categoria categoria = categoriaRepository.findById(idCatNotNull)
                .orElseThrow(() -> new BusquedaNotFoundException("Categoría no encontrada con id: " + idCatNotNull));
        return mapper.toCategoriaResponse(categoria);
    }

    public List<CategoriaResponse> searchCategorias(String nombre) {
        String nombreNotNull = Objects.requireNonNull(nombre, "El nombre es obligatorio");
        return mapper.toCategoriaResponseList(categoriaRepository.findByNombreContainingIgnoreCase(nombreNotNull));
    }

    @Transactional
    public CategoriaResponse createCategoria(CategoriaRequest request) {
        Objects.requireNonNull(request, "La solicitud de categoría es obligatoria");
        if (categoriaRepository.existsByNombreIgnoreCase(request.getNombre())) {
            throw new IllegalArgumentException("Ya existe una categoría con nombre: " + request.getNombre());
        }
        Categoria categoria = Categoria.builder().nombre(request.getNombre()).build();
        return mapper.toCategoriaResponse(categoriaRepository.save(categoria));
    }

    public List<PeliculaResponse> findAllPeliculas() {
        return mapper.toPeliculaResponseList(peliculaRepository.findAll());
    }

    public PeliculaResponse findPeliculaBySlug(String slug) {
        String slugNotNull = Objects.requireNonNull(slug, "El slug de la película es obligatorio");
        Pelicula pelicula = peliculaRepository.findById(slugNotNull)
                .orElseThrow(() -> new BusquedaNotFoundException("Película no encontrada con slug: " + slugNotNull));
        return mapper.toPeliculaResponse(pelicula);
    }

    public List<PeliculaResponse> searchPeliculasByTitulo(String titulo) {
        String tituloNotNull = Objects.requireNonNull(titulo, "El título es obligatorio");
        return mapper.toPeliculaResponseList(peliculaRepository.findByTituloContainingIgnoreCase(tituloNotNull));
    }

    public List<PeliculaResponse> findPeliculasByCategoria(Integer idCat) {
        Integer idCatNotNull = Objects.requireNonNull(idCat, "El ID de categoría es obligatorio");
        return mapper.toPeliculaResponseList(peliculaRepository.findByCategoria_IdCat(idCatNotNull));
    }

    public List<PeliculaResponse> findPeliculasByEstrenoAnio(Integer estrenoAnio) {
        Integer estrenoAnioNotNull = Objects.requireNonNull(estrenoAnio, "El año de estreno es obligatorio");
        return mapper.toPeliculaResponseList(peliculaRepository.findByEstrenoAnio(estrenoAnioNotNull));
    }

    @Transactional
    public PeliculaResponse createPelicula(PeliculaRequest request) {
        Objects.requireNonNull(request, "La solicitud de película es obligatoria");
        if (peliculaRepository.existsById(request.getSlug())) {
            throw new IllegalArgumentException("Ya existe una película con slug: " + request.getSlug());
        }

        Categoria categoria = categoriaRepository.findById(request.getIdCat())
                .orElseThrow(() -> new BusquedaNotFoundException("Categoría no encontrada con id: " + request.getIdCat()));

        Pelicula pelicula = Pelicula.builder()
                .slug(request.getSlug())
                .titulo(request.getTitulo())
                .categoria(categoria)
                .duracionMin(request.getDuracionMin())
                .estrenoAnio(request.getEstrenoAnio())
                .build();

        pelicula = peliculaRepository.save(pelicula);
        log.info("Pelicula creada: {}", pelicula.getSlug());
        eventProducer.publishPeliculaCreated(pelicula.getSlug(), pelicula.getTitulo(), categoria.getIdCat(), pelicula.getDuracionMin(), pelicula.getEstrenoAnio());
        return mapper.toPeliculaResponse(pelicula);
    }

    @Transactional
    public PeliculaResponse updatePelicula(String slug, PeliculaRequest request) {
        String slugNotNull = Objects.requireNonNull(slug, "El slug de la película es obligatorio");
        Objects.requireNonNull(request, "La solicitud de película es obligatoria");
        Pelicula existing = peliculaRepository.findById(slugNotNull)
                .orElseThrow(() -> new BusquedaNotFoundException("Película no encontrada con slug: " + slugNotNull));

        if (!slugNotNull.equals(request.getSlug())) {
            throw new IllegalArgumentException("El slug de la ruta y el cuerpo deben coincidir");
        }

        Categoria categoria = categoriaRepository.findById(request.getIdCat())
                .orElseThrow(() -> new BusquedaNotFoundException("Categoría no encontrada con id: " + request.getIdCat()));

        existing.setTitulo(request.getTitulo());
        existing.setCategoria(categoria);
        existing.setDuracionMin(request.getDuracionMin());
        existing.setEstrenoAnio(request.getEstrenoAnio());

        Pelicula updated = peliculaRepository.save(existing);
        log.info("Pelicula actualizada: {}", updated.getSlug());
        eventProducer.publishPeliculaUpdated(updated.getSlug(), updated.getTitulo(), categoria.getIdCat(), updated.getDuracionMin(), updated.getEstrenoAnio());
        return mapper.toPeliculaResponse(updated);
    }

    @Transactional
    public void deletePelicula(String slug) {
        String slugNotNull = Objects.requireNonNull(slug, "El slug de la película es obligatorio");
        if (!peliculaRepository.existsById(slugNotNull)) {
            throw new BusquedaNotFoundException("Película no encontrada con slug: " + slugNotNull);
        }
        peliculaRepository.deleteById(slugNotNull);
        log.info("Pelicula eliminada: {}", slugNotNull);
        eventProducer.publishPeliculaDeleted(slugNotNull);
    }

    public UsuarioProyeccionResponse findUsuarioByEmail(String email) {
        String emailNotNull = Objects.requireNonNull(email, "El email es obligatorio");
        UsuarioProyeccion usuario = usuarioRepository.findById(emailNotNull)
                .orElseThrow(() -> new BusquedaNotFoundException("Usuario de proyección no encontrado con email: " + emailNotNull));
        return mapper.toUsuarioProyeccionResponse(usuario);
    }
}
