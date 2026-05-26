package cl.ticketcine.reserva.service;

import cl.ticketcine.reserva.dto.mantenimientoRequest;
import cl.ticketcine.reserva.dto.mantenimientoResponse;
import cl.ticketcine.reserva.exception.mantenimientoNotFoundException;
import cl.ticketcine.reserva.exception.salaNotFoundException;
import cl.ticketcine.reserva.mapper.MantenimientoMapper;
import cl.ticketcine.reserva.model.Mantenimiento;
import cl.ticketcine.reserva.model.Sala;
import cl.ticketcine.reserva.repository.mantenimientoRepository;
import cl.ticketcine.reserva.repository.salaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MantenimientoService {

    private final mantenimientoRepository mantenimientoRepository;
    private final salaRepository salaRepository;
    private final MantenimientoMapper mantenimientoMapper;

    public List<mantenimientoResponse> findAll() {
        return mantenimientoMapper.toResponseList(mantenimientoRepository.findAll());
    }

    public mantenimientoResponse findById(Integer idMaint) {
        Mantenimiento mantenimiento = mantenimientoRepository.findByIdMaint(idMaint)
                .orElseThrow(() -> new mantenimientoNotFoundException(idMaint));
        return mantenimientoMapper.toResponse(mantenimiento);
    }

    public List<mantenimientoResponse> findBySalaId(String idSala) {
        String idNotNull = Objects.requireNonNull(idSala, "El id de la sala es obligatorio");
        if (!salaRepository.existsByIdSala(idNotNull)) {
            throw new salaNotFoundException(idNotNull);
        }
        return mantenimientoMapper.toResponseList(mantenimientoRepository.findBySalaIdSala(idNotNull));
    }

    public mantenimientoResponse create(mantenimientoRequest request) {
        Objects.requireNonNull(request, "La solicitud de mantenimiento es obligatoria");

        Sala sala = salaRepository.findByIdSala(request.getIdSala())
                .orElseThrow(() -> new salaNotFoundException(request.getIdSala()));

        Mantenimiento mantenimiento = mantenimientoMapper.toEntity(request);
        mantenimiento.setSala(sala);

        Mantenimiento saved = mantenimientoRepository.save(mantenimiento);
        return mantenimientoMapper.toResponse(saved);
    }

    public mantenimientoResponse update(Integer idMaint, mantenimientoRequest request) {
        Objects.requireNonNull(request, "La solicitud de mantenimiento es obligatoria");

        Mantenimiento mantenimiento = mantenimientoRepository.findByIdMaint(idMaint)
                .orElseThrow(() -> new mantenimientoNotFoundException(idMaint));

        if (!Objects.equals(mantenimiento.getSala().getIdSala(), request.getIdSala())) {
            Sala sala = salaRepository.findByIdSala(request.getIdSala())
                    .orElseThrow(() -> new salaNotFoundException(request.getIdSala()));
            mantenimiento.setSala(sala);
        }

        mantenimientoMapper.updateEntity(request, mantenimiento);
        Mantenimiento updated = mantenimientoRepository.save(mantenimiento);
        return mantenimientoMapper.toResponse(updated);
    }

    public void deleteById(Integer idMaint) {
        if (!mantenimientoRepository.existsByIdMaint(idMaint)) {
            throw new mantenimientoNotFoundException(idMaint);
        }
        mantenimientoRepository.deleteById(idMaint);
    }
}
