package cl.ticketcine.salas.service;

import cl.ticketcine.salas.dto.SalaRequest;
import cl.ticketcine.salas.dto.SalaResponse;
import cl.ticketcine.salas.exception.SalaNotFoundException;
import cl.ticketcine.salas.mapper.SalaMapper;
import cl.ticketcine.salas.model.entity.Sala;
import cl.ticketcine.salas.repository.SalaRepository;
import cl.ticketcine.salas.event.SalaEventProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SalaService {

    private final SalaRepository salaRepository;
    private final SalaMapper salaMapper;
    private final SalaEventProducer salaEventProducer;

    public List<SalaResponse> findAll() {
        return salaMapper.toResponseList(salaRepository.findAll());
    }

    public SalaResponse findById(String idSala) {
        String idSalaNotNull = Objects.requireNonNull(idSala, "El ID de la sala es obligatorio");
        Sala sala = salaRepository.findById(idSalaNotNull)
                .orElseThrow(() -> new SalaNotFoundException(idSalaNotNull));
        return salaMapper.toResponse(sala);
    }

    public List<SalaResponse> findByFormato(String formato) {
        String formatoNotNull = Objects.requireNonNull(formato, "El formato es obligatorio");
        return salaMapper.toResponseList(salaRepository.findByFormato(formatoNotNull));
    }

    public List<SalaResponse> findByCapacidadMin(Integer capacidad) {
        Integer capacidadNotNull = Objects.requireNonNull(capacidad, "La capacidad mínima es obligatoria");
        return salaMapper.toResponseList(salaRepository.findByCapacidadGreaterThanEqual(capacidadNotNull));
    }

    @Transactional
    public SalaResponse create(SalaRequest request) {
        Objects.requireNonNull(request, "La solicitud de sala es obligatoria");
        if (salaRepository.existsById(request.getIdSala())) {
            throw new IllegalArgumentException("Ya existe una sala con id: " + request.getIdSala());
        }
        Sala sala = salaMapper.toEntity(request);
        sala = salaRepository.save(sala);
        log.info("Sala creada: {}", sala.getIdSala());
        salaEventProducer.publishSalaCreated(sala.getIdSala(), sala.getFormato(), sala.getCapacidad());
        return salaMapper.toResponse(sala);
    }

    @Transactional
    public SalaResponse update(String idSala, SalaRequest request) {
        String idSalaNotNull = Objects.requireNonNull(idSala, "El ID de la sala es obligatorio");
        Objects.requireNonNull(request, "La solicitud de sala es obligatoria");
        Sala existente = salaRepository.findById(idSalaNotNull)
                .orElseThrow(() -> new SalaNotFoundException(idSalaNotNull));

        if (!idSalaNotNull.equals(request.getIdSala())) {
            throw new IllegalArgumentException("El id de sala en la ruta y en el cuerpo deben coincidir");
        }

        existente.setFormato(request.getFormato());
        existente.setCapacidad(request.getCapacidad());
        Sala actualizado = salaRepository.save(existente);
        log.info("Sala actualizada: {}", actualizado.getIdSala());
        salaEventProducer.publishSalaUpdated(actualizado.getIdSala(), actualizado.getFormato(), actualizado.getCapacidad());
        return salaMapper.toResponse(actualizado);
    }

    @Transactional
    public void deleteById(String idSala) {
        String idSalaNotNull = Objects.requireNonNull(idSala, "El ID de la sala es obligatorio");
        if (!salaRepository.existsById(idSalaNotNull)) {
            throw new SalaNotFoundException(idSalaNotNull);
        }
        salaRepository.deleteById(idSalaNotNull);
        log.info("Sala eliminada: {}", idSalaNotNull);
        salaEventProducer.publishSalaDeleted(idSalaNotNull);
    }
}
