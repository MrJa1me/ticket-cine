package cl.ticketcine.horarios.service;

import cl.ticketcine.horarios.dto.HorarioRequest;
import cl.ticketcine.horarios.dto.HorarioResponse;
import cl.ticketcine.horarios.exceptions.DuplicateResourceException;
import cl.ticketcine.horarios.exceptions.EntityNotFoundException;
import cl.ticketcine.horarios.mapper.HorarioMapper;
import cl.ticketcine.horarios.model.Horario;
import cl.ticketcine.horarios.repository.HorarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HorarioService {

    private final HorarioRepository horarioRepository;
    private final HorarioMapper horarioMapper;

    public List<HorarioResponse> findAll() {
        return horarioMapper.toResponseList(horarioRepository.findAll());
    }

    public HorarioResponse findById(Long id) {
        return horarioMapper.toResponse(findExistingById(id));
    }

    public List<HorarioResponse> findByFecha(LocalDate fecha) {
        return horarioMapper.toResponseList(horarioRepository.findByFecha(fecha));
    }

    public List<HorarioResponse> findByPelicula(String pelicula) {
        return horarioMapper.toResponseList(horarioRepository.findByPeliculaContainingIgnoreCase(pelicula));
    }

    @Transactional
    public HorarioResponse create(HorarioRequest request) {
        validateHorarioNotDuplicated(request, null);

        Horario horario = horarioMapper.toEntity(request);
        horario.setHabilitado(true);

        return horarioMapper.toResponse(horarioRepository.save(horario));
    }

    @Transactional
    public HorarioResponse update(Long id, HorarioRequest request) {
        Horario existente = findExistingById(id);
        validateHorarioNotDuplicated(request, id);

        horarioMapper.updateEntity(request, existente);

        return horarioMapper.toResponse(horarioRepository.save(existente));
    }

    @Transactional
    public void deleteById(Long id) {
        Horario horario = findExistingById(id);
        horarioRepository.delete(horario);
    }

    @Transactional
    public HorarioResponse habilitar(Long id) {
        Horario existente = findExistingById(id);
        existente.setHabilitado(true);
        return horarioMapper.toResponse(horarioRepository.save(existente));
    }

    @Transactional
    public HorarioResponse deshabilitar(Long id) {
        Horario existente = findExistingById(id);
        existente.setHabilitado(false);
        return horarioMapper.toResponse(horarioRepository.save(existente));
    }

    private void validateHorarioNotDuplicated(HorarioRequest request, Long existingId) {
        horarioRepository.findByPeliculaIgnoreCaseAndFechaAndHora(request.getPelicula(), request.getFecha(), request.getHora())
                .filter(horario -> existingId == null || !horario.getId().equals(existingId))
                .ifPresent(horario -> {
                    throw new DuplicateResourceException(
                            "El horario de la película '" + request.getPelicula() + "' " +
                                    "ya existe para " + request.getFecha() + " a las " + request.getHora() + "."
                    );
                });
    }

    private Horario findExistingById(Long id) {
        return horarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el horario con id " + id));
    }
}
