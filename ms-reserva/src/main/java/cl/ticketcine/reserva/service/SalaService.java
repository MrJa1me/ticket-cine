package cl.ticketcine.reserva.service;

import cl.ticketcine.reserva.dto.salaRequest;
import cl.ticketcine.reserva.dto.salaResponse;
import cl.ticketcine.reserva.exception.salaNotFoundException;
import cl.ticketcine.reserva.mapper.SalaMapper;
import cl.ticketcine.reserva.model.Sala;
import cl.ticketcine.reserva.repository.salaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SalaService {

    private final salaRepository salaRepository;
    private final SalaMapper salaMapper;

    public List<salaResponse> findAll() {
        return salaMapper.toResponseList(salaRepository.findAll());
    }

    public salaResponse findById(String idSala) {
        String idNotNull = Objects.requireNonNull(idSala, "El id de la sala es obligatorio");
        Sala sala = salaRepository.findByIdSala(idNotNull)
                .orElseThrow(() -> new salaNotFoundException(idNotNull));
        return salaMapper.toResponse(sala);
    }

    public salaResponse create(salaRequest request) {
        Objects.requireNonNull(request, "La solicitud de sala es obligatoria");
        if (salaRepository.existsByIdSala(request.getIdSala())) {
            throw new IllegalArgumentException("La sala con ID " + request.getIdSala() + " ya existe");
        }

        Sala sala = salaMapper.toEntity(request);
        Sala saved = salaRepository.save(sala);
        return salaMapper.toResponse(saved);
    }

    public salaResponse update(String idSala, salaRequest request) {
        String idNotNull = Objects.requireNonNull(idSala, "El id de la sala es obligatorio");
        Objects.requireNonNull(request, "La solicitud de sala es obligatoria");

        Sala sala = salaRepository.findByIdSala(idNotNull)
                .orElseThrow(() -> new salaNotFoundException(idNotNull));

        salaMapper.updateEntity(request, sala);
        Sala updated = salaRepository.save(sala);
        return salaMapper.toResponse(updated);
    }

    public void deleteById(String idSala) {
        String idNotNull = Objects.requireNonNull(idSala, "El id de la sala es obligatorio");
        if (!salaRepository.existsByIdSala(idNotNull)) {
            throw new salaNotFoundException(idNotNull);
        }
        salaRepository.deleteById(idNotNull);
    }
}
