package cl.ticketcine.salas.service;

import cl.ticketcine.salas.dto.SalaRequest;
import cl.ticketcine.salas.dto.SalaResponse;
import cl.ticketcine.salas.exception.SalaNotFoundException;
import cl.ticketcine.salas.mapper.SalaMapper;
import cl.ticketcine.salas.model.entity.Sala;
import cl.ticketcine.salas.repository.SalaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SalaService {

    private final SalaRepository salaRepository;
    private final SalaMapper salaMapper;

    public List<SalaResponse> findAll() {
        return salaMapper.toResponseList(salaRepository.findAll());
    }

    public SalaResponse findById(String idSala) {
        Sala sala = salaRepository.findById(idSala)
                .orElseThrow(() -> new SalaNotFoundException(idSala));
        return salaMapper.toResponse(sala);
    }

    public List<SalaResponse> findByFormato(String formato) {
        return salaMapper.toResponseList(salaRepository.findByFormato(formato));
    }

    public List<SalaResponse> findByCapacidadMin(Integer capacidad) {
        return salaMapper.toResponseList(salaRepository.findByCapacidadGreaterThanEqual(capacidad));
    }

    @Transactional
    public SalaResponse create(SalaRequest request) {
        if (salaRepository.existsById(request.getIdSala())) {
            throw new IllegalArgumentException("Ya existe una sala con id: " + request.getIdSala());
        }
        Sala sala = salaMapper.toEntity(request);
        return salaMapper.toResponse(salaRepository.save(sala));
    }

    @Transactional
    public SalaResponse update(String idSala, SalaRequest request) {
        Sala existente = salaRepository.findById(idSala)
                .orElseThrow(() -> new SalaNotFoundException(idSala));

        if (!idSala.equals(request.getIdSala())) {
            throw new IllegalArgumentException("El id de sala en la ruta y en el cuerpo deben coincidir");
        }

        existente.setFormato(request.getFormato());
        existente.setCapacidad(request.getCapacidad());
        Sala actualizado = salaRepository.save(existente);
        return salaMapper.toResponse(actualizado);
    }

    @Transactional
    public void deleteById(String idSala) {
        if (!salaRepository.existsById(idSala)) {
            throw new SalaNotFoundException(idSala);
        }
        salaRepository.deleteById(idSala);
    }
}
