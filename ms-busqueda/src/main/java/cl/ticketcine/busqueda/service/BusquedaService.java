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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BusquedaService {

    private final CategoriaRepository categoriaRepository;
    private final PeliculaRepository peliculaRepository;
    private final UsuarioProyeccionRepository usuarioRepository;
    private final BusquedaMapper mapper;

    public List<CategoriaResponse> findAllCategorias() {
        return mapper.toCategoriaResponseList(categoriaRepository.findAll());
    }

    public CategoriaResponse findCategoriaById(Integer idCat) {
        Categoria categoria = categoriaRepository.findById(idCat)
                .orElseThrow(() -> new BusquedaNotFoundException("Categoría no encontrada con id: " + idCat));
        return mapper.toCategoriaResponse(categoria);
    }

    public List<CategoriaResponse> searchCategorias(String nombre) {
        return mapper.toCategoriaResponseList(categoriaRepository.findByNombreContainingIgnoreCase(nombre));
    }

    @Transactional
    public CategoriaResponse createCategoria(CategoriaRequest request) {
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
        Pelicula pelicula = peliculaRepository.findById(slug)
                .orElseThrow(() -> new BusquedaNotFoundException("Película no encontrada con slug: " + slug));
        return mapper.toPeliculaResponse(pelicula);
    }

    public List<PeliculaResponse> searchPeliculasByTitulo(String titulo) {
        return mapper.toPeliculaResponseList(peliculaRepository.findByTituloContainingIgnoreCase(titulo));
    }

    public List<PeliculaResponse> findPeliculasByCategoria(Integer idCat) {
        return mapper.toPeliculaResponseList(peliculaRepository.findByCategoria_IdCat(idCat));
    }

    public List<PeliculaResponse> findPeliculasByEstrenoAnio(Integer estrenoAnio) {
        return mapper.toPeliculaResponseList(peliculaRepository.findByEstrenoAnio(estrenoAnio));
    }

    @Transactional
    public PeliculaResponse createPelicula(PeliculaRequest request) {
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

        return mapper.toPeliculaResponse(peliculaRepository.save(pelicula));
    }

    @Transactional
    public PeliculaResponse updatePelicula(String slug, PeliculaRequest request) {
        Pelicula existing = peliculaRepository.findById(slug)
                .orElseThrow(() -> new BusquedaNotFoundException("Película no encontrada con slug: " + slug));

        if (!slug.equals(request.getSlug())) {
            throw new IllegalArgumentException("El slug de la ruta y el cuerpo deben coincidir");
        }

        Categoria categoria = categoriaRepository.findById(request.getIdCat())
                .orElseThrow(() -> new BusquedaNotFoundException("Categoría no encontrada con id: " + request.getIdCat()));

        existing.setTitulo(request.getTitulo());
        existing.setCategoria(categoria);
        existing.setDuracionMin(request.getDuracionMin());
        existing.setEstrenoAnio(request.getEstrenoAnio());

        return mapper.toPeliculaResponse(peliculaRepository.save(existing));
    }

    @Transactional
    public void deletePelicula(String slug) {
        if (!peliculaRepository.existsById(slug)) {
            throw new BusquedaNotFoundException("Película no encontrada con slug: " + slug);
        }
        peliculaRepository.deleteById(slug);
    }

    public UsuarioProyeccionResponse findUsuarioByEmail(String email) {
        UsuarioProyeccion usuario = usuarioRepository.findById(email)
                .orElseThrow(() -> new BusquedaNotFoundException("Usuario de proyección no encontrado con email: " + email));
        return mapper.toUsuarioProyeccionResponse(usuario);
    }
}
