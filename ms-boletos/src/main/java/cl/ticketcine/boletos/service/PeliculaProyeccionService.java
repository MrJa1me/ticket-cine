package cl.ticketcine.boletos.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import cl.ticketcine.boletos.dto.ProyPeliculaResponse;
import cl.ticketcine.boletos.mapper.ProyPeliculaMapper;
import cl.ticketcine.boletos.model.ProyPelicula;
import cl.ticketcine.boletos.repository.BoletoRepository;
import cl.ticketcine.boletos.repository.ProyPeliculaRepository;
import cl.ticketcine.common.exception.EntityNotFoundException;
import cl.ticketcine.common.exception.ReferentialIntegrityException;

@Slf4j
@Service
@RequiredArgsConstructor
public class PeliculaProyeccionService {

    private final ProyPeliculaRepository proyPeliculaRepository;
    private final BoletoRepository boletoRepository;
    private final ProyPeliculaMapper proyPeliculaMapper;

    @Transactional
    public List<ProyPeliculaResponse> findAll() {
        return proyPeliculaMapper.toResponseList(proyPeliculaRepository.findAll());
    }

    @Transactional
    public ProyPeliculaResponse findById(Integer id) {
        return proyPeliculaRepository.findById(id)
                .map(proyPeliculaMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Pelicula Proyección", "ID", id.toString()));
    }

    @Transactional
    public void save(Integer idPelicula, String nombrePelicula, LocalDate fecha) {
        ProyPelicula Pelicula = proyPeliculaRepository.findById(idPelicula)
                .orElse(ProyPelicula.builder().idPelicula(idPelicula).build());
        Pelicula.setNombrePelicula(nombrePelicula);
        if (fecha != null) {
            Pelicula.setFecha(fecha);
        }
        proyPeliculaRepository.save(Pelicula);
    }

    @Transactional
    public void eliminarProyeccion(Integer id) {
        proyPeliculaRepository.findById(id).ifPresent(Pelicula -> {
            if (boletoRepository.existsByPeliculaIdPelicula(id)) {
                log.warn("No se elimina la proyección del Pelicula {}: tiene boletos locales", id);
                return;
            }
            proyPeliculaRepository.delete(Pelicula);
        });
    }

    @Transactional
    public void deleteById(Integer id) {
        ProyPelicula Pelicula = proyPeliculaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pelicula", "ID", id.toString()));

        List<String> tablasAsociadas = new ArrayList<>();

        if (boletoRepository.existsByPeliculaIdPelicula(id)) {
            tablasAsociadas.add("Boletos emitidos localmente");
        }

        if (!tablasAsociadas.isEmpty()) {
            throw new ReferentialIntegrityException("Pelicula Proyección", id, String.join(", ", tablasAsociadas));
        }

        proyPeliculaRepository.delete(Pelicula);
    }
}
