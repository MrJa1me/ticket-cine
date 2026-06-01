package cl.ticketcine.reserva.service;

import cl.ticketcine.reserva.dto.MantenimientoRequest;
import cl.ticketcine.reserva.dto.MantenimientoResponse;
import cl.ticketcine.reserva.exception.MantenimientoNotFoundException;
import cl.ticketcine.reserva.exception.SalaNotFoundException;
import cl.ticketcine.reserva.mapper.MantenimientoMapper;
import cl.ticketcine.reserva.model.Mantenimiento;
import cl.ticketcine.reserva.model.Sala;
import cl.ticketcine.reserva.repository.MantenimientoRepository;
import cl.ticketcine.reserva.repository.SalaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MantenimientoService {

    private final MantenimientoRepository mantenimientoRepository;
    private final SalaRepository salaRepository;
    private final MantenimientoMapper mantenimientoMapper;

    public List<MantenimientoResponse> findAll() {
        return mantenimientoMapper.toResponseList(mantenimientoRepository.findAll());
    }

    public MantenimientoResponse findById(Integer idMaint) {
        Mantenimiento mantenimiento = mantenimientoRepository.findByIdMaint(idMaint)
                .orElseThrow(() -> new MantenimientoNotFoundException(idMaint));
        return mantenimientoMapper.toResponse(mantenimiento);
    }

    public List<MantenimientoResponse> findBySalaId(String idSala) {
        List<Mantenimiento> mantenimientos = mantenimientoRepository.findBySalaIdSala(idSala);
        if (mantenimientos.isEmpty()) {
            throw new SalaNotFoundException(idSala);
        }
        return mantenimientoMapper.toResponseList(mantenimientos);
    }

    public MantenimientoResponse create(MantenimientoRequest request) {
        Sala sala = salaRepository.findById(request.getIdSala())
                .orElseThrow(() -> new SalaNotFoundException(request.getIdSala()));

        Mantenimiento mantenimiento = mantenimientoMapper.toEntity(request);
        mantenimiento.setSala(sala);

        Mantenimiento saved = mantenimientoRepository.save(mantenimiento);
        return mantenimientoMapper.toResponse(saved);
    }

    public MantenimientoResponse update(Integer idMaint, MantenimientoRequest request) {
        Mantenimiento existing = mantenimientoRepository.findByIdMaint(idMaint)
                .orElseThrow(() -> new MantenimientoNotFoundException(idMaint));

        Sala sala = salaRepository.findById(request.getIdSala())
                .orElseThrow(() -> new SalaNotFoundException(request.getIdSala()));

        mantenimientoMapper.updateEntity(request, existing);
        existing.setSala(sala);

        Mantenimiento updated = mantenimientoRepository.save(existing);
        return mantenimientoMapper.toResponse(updated);
    }

    public void deleteById(Integer idMaint) {
        mantenimientoRepository.findByIdMaint(idMaint)
                .orElseThrow(() -> new MantenimientoNotFoundException(idMaint));
        mantenimientoRepository.deleteById(idMaint);
    }
}
