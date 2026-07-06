package cl.ticketcine.catalogo.service;

import cl.ticketcine.catalogo.dto.PeliculaRequestDTO;
import cl.ticketcine.catalogo.dto.PeliculaResponseDTO;
import cl.ticketcine.catalogo.event.PeliculaEventProducer;
import cl.ticketcine.catalogo.mapper.PeliculaMapper;
import cl.ticketcine.catalogo.model.Pelicula;
import cl.ticketcine.catalogo.repository.PeliculaRepository;
import cl.ticketcine.common.event.PeliculaCreatedEvent;
import cl.ticketcine.common.event.PeliculaDeletedEvent;
import cl.ticketcine.common.event.PeliculaUpdatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PeliculaService {

    private final PeliculaRepository repository;
    private final PeliculaMapper mapper;
    private final PeliculaEventProducer PeliculaEventProducer;

    public List<PeliculaResponseDTO> obtenerTodos() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    public PeliculaResponseDTO buscarPorId(Integer id) {
        return repository.findById(id)
                .map(mapper::toResponseDTO)
                .orElseThrow(() -> new RuntimeException("La Pel\u00edcula con ID " + id + " no existe."));
    }

    public PeliculaResponseDTO crear(PeliculaRequestDTO request) {
        if (repository.existsByNombrePeliculaIgnoreCaseAndFecha(request.getNombrePelicula(), request.getFecha())) {
            throw new IllegalArgumentException("Ya existe una Pel\u00edcula registrada con ese nombre para la fecha " + request.getFecha());
        }

        Pelicula pelicula = mapper.toEntity(request);
        Pelicula guardado = repository.save(pelicula);

        PeliculaEventProducer.sendCreated(PeliculaCreatedEvent.builder()
                .idPelicula(guardado.getIdPelicula())
                .nombrePelicula(guardado.getNombrePelicula())
                .categoria(guardado.getCategoria())
                .fecha(guardado.getFecha())
                .estado(guardado.getEstado())
                .build());

        return mapper.toResponseDTO(guardado);
    }

    public PeliculaResponseDTO actualizar(Integer id, PeliculaRequestDTO request) {
        Pelicula peliculaExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("La Pel\u00edcula con ID " + id + " no existe."));

        if (repository.existsByNombrePeliculaIgnoreCaseAndFechaAndIdPeliculaNot(
                request.getNombrePelicula(), request.getFecha(), id)) {
            throw new IllegalArgumentException("Ya existe otra Pel\u00edcula registrada con ese nombre para la fecha " + request.getFecha());
        }

        mapper.updateFromRequest(request, peliculaExistente);
        Pelicula guardado = repository.save(peliculaExistente);

        PeliculaEventProducer.sendUpdated(PeliculaUpdatedEvent.builder()
                .idPelicula(guardado.getIdPelicula())
                .nombrePelicula(guardado.getNombrePelicula())
                .categoria(guardado.getCategoria())
                .fecha(guardado.getFecha())
                .estado(guardado.getEstado())
                .build());

        return mapper.toResponseDTO(guardado);
    }

    public void eliminar(Integer id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar. La Pel\u00edcula con ID " + id + " no existe.");
        }
        repository.deleteById(id);
        PeliculaEventProducer.sendDeleted(PeliculaDeletedEvent.builder().idPelicula(id).build());
    }

    public boolean existePorId(Integer idPelicula) {
        return repository.existsById(idPelicula);
    }

}
