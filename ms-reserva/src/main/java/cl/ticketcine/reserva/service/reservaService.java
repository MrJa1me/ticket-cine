package cl.ticketcine.reserva.service;


import java.util.List;

import org.springframework.stereotype.Service;

import cl.ticketcine.reserva.dto.ReservaRequest;
import cl.ticketcine.reserva.dto.ReservaResponse;
import cl.ticketcine.reserva.exception.asientoNotFoundException;
import cl.ticketcine.reserva.exception.salaNotFoundException;
import cl.ticketcine.reserva.mapper.ReservaMapper;
import cl.ticketcine.reserva.model.Asiento;
import cl.ticketcine.reserva.model.Sala;
import cl.ticketcine.reserva.repository.ReservaRepository;

import lombok.RequiredArgsConstructor;

/**
 * Servicio encargado de la lógica de negocio relacionada con asientos y reservas.
 * Gestiona operaciones CRUD, validaciones de negocio y reglas de integridad.
 */
@Service
@RequiredArgsConstructor
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final ReservaMapper reservaMapper;

    public List<ReservaResponse> findAll() {
        return reservaMapper.toResponseList(reservaRepository.findAll());
    }

    public ReservaResponse findById(Integer idAsiento) {
        Asiento asiento = reservaRepository.findByIdAsiento(idAsiento)
                .orElseThrow(() -> new asientoNotFoundException(idAsiento));
        return reservaMapper.toResponse(asiento);
    }

    public List<ReservaResponse> findBySalaId(String idSala) {
        List<Asiento> asientos = reservaRepository.findBySalaIdSala(idSala);
        if (asientos.isEmpty()) {
            throw new salaNotFoundException(idSala);
        }
        return reservaMapper.toResponseList(asientos);
    }

    public ReservaResponse create(ReservaRequest request) {

        if (reservaRepository.existsByIdAsiento(request.getIdAsiento())) {
            throw new asientoNotFoundException(
                    "El asiento con ID " + request.getIdAsiento() + " ya existe");
        }

        Asiento asiento = reservaMapper.toModel(request);

        if (asiento == null) {
            throw new IllegalArgumentException("La solicitud de asiento no pudo ser procesada.");
        }

        // Aquí normalmente se buscaría la sala en una tabla de salas
        // Para este ejemplo, se deja como estructura base
        Sala sala = new Sala();
        sala.setIdSala(request.getIdSala());
        asiento.setSala(sala);
        asiento.setFila(request.getFila());
        asiento.setNumero(request.getNumero());

        Asiento guardado = reservaRepository.save(asiento);
        return reservaMapper.toResponse(guardado);
    }

    public ReservaResponse update(Integer idAsiento, ReservaRequest request) {

        Asiento existente = reservaRepository.findByIdAsiento(idAsiento)
                .orElseThrow(() -> new asientoNotFoundException(idAsiento));

        existente.setFila(request.getFila());
        existente.setNumero(request.getNumero());

        Asiento guardado = reservaRepository.save(existente);
        return reservaMapper.toResponse(guardado);
    }

    public void deleteById(Integer idAsiento) {

        reservaRepository.findByIdAsiento(idAsiento)
                .orElseThrow(() -> new asientoNotFoundException(idAsiento));

        reservaRepository.deleteById(idAsiento);
    }
}
