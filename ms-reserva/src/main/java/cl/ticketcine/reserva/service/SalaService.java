package cl.ticketcine.reserva.service;

import cl.ticketcine.reserva.dto.SalaRequest;
import cl.ticketcine.reserva.dto.SalaResponse;
import cl.ticketcine.reserva.exception.SalaNotFoundException;
import cl.ticketcine.reserva.mapper.SalaMapper;
import cl.ticketcine.reserva.model.Sala;
import cl.ticketcine.reserva.repository.SalaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SalaService {

    private final SalaRepository salaRepository;
    private final SalaMapper salaMapper;

    public List<SalaResponse> findAll() {
        return salaMapper.toResponseList(salaRepository.findAll());
    }

    public SalaResponse findById(String idSala) {
        String idNotNull = Objects.requireNonNull(idSala, "El id de la sala es obligatorio");
        Sala sala = salaRepository.findByIdSala(idNotNull)
                .orElseThrow(() -> new SalaNotFoundException(idNotNull));
        return salaMapper.toResponse(sala);
    }

    public SalaResponse create(SalaRequest request) {
        Objects.requireNonNull(request, "La solicitud de sala es obligatoria");
        if (salaRepository.existsByIdSala(request.getIdSala())) {
            throw new IllegalArgumentException("La sala con ID " + request.getIdSala() + " ya existe");
        }

        Sala sala = salaMapper.toEntity(request);
        Sala saved = salaRepository.save(sala);
        return salaMapper.toResponse(saved);
    }

    public SalaResponse update(String idSala, SalaRequest request) {
        String idNotNull = Objects.requireNonNull(idSala, "El id de la sala es obligatorio");
        Objects.requireNonNull(request, "La solicitud de sala es obligatoria");

        Sala sala = salaRepository.findByIdSala(idNotNull)
                .orElseThrow(() -> new SalaNotFoundException(idNotNull));

        salaMapper.updateEntity(request, sala);
        Sala updated = salaRepository.save(sala);
        return salaMapper.toResponse(updated);
    }

    public void deleteById(String idSala) {
        String idNotNull = Objects.requireNonNull(idSala, "El id de la sala es obligatorio");
        if (!salaRepository.existsByIdSala(idNotNull)) {
            throw new SalaNotFoundException(idNotNull);
        }
        salaRepository.deleteById(idNotNull);
    }
}
