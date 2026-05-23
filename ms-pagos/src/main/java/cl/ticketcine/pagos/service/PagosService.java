package cl.ticketcine.pagos.service;

import cl.ticketcine.pagos.dto.PagosRequest;
import cl.ticketcine.pagos.dto.PagosResponse;
import cl.ticketcine.pagos.exception.MetodoPagoNotFoundException;
import cl.ticketcine.pagos.exception.TransaccionNotFoundException;
import cl.ticketcine.pagos.exception.ReservasProyeccionNotFoundException;
import cl.ticketcine.pagos.mapper.PagosMapper;
import cl.ticketcine.pagos.model.Transaccion;
import cl.ticketcine.pagos.repository.MetodoPagoRepository;
import cl.ticketcine.pagos.repository.ReservasProyeccionRepository;
import cl.ticketcine.pagos.repository.TransaccionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PagosService {

    private final TransaccionRepository transaccionRepository;
    private final MetodoPagoRepository metodoPagoRepository;
    private final ReservasProyeccionRepository reservasRepository;
    private final PagosMapper pagosMapper;

    public List<PagosResponse> findAll() {
        return pagosMapper.toResponseList(transaccionRepository.findAll());
    }

    public PagosResponse findById(Long id) {
        Transaccion t = transaccionRepository.findById(id)
                .orElseThrow(() -> new TransaccionNotFoundException(id));
        return pagosMapper.toResponse(t);
    }

    public PagosResponse create(PagosRequest request) {
        // validar existencia de reserva
        if (!reservasRepository.existsById(request.getReservaId())) {
            throw new ReservasProyeccionNotFoundException(request.getReservaId());
        }

        // validar existencia de metodo de pago
        if (!metodoPagoRepository.existsById(request.getMetodoId())) {
            throw new MetodoPagoNotFoundException(request.getMetodoId());
        }

        Transaccion t = pagosMapper.toEntity(request);
        Transaccion saved = transaccionRepository.save(t);
        return pagosMapper.toResponse(saved);
    }

    public PagosResponse update(Long id, PagosRequest request) {
        Transaccion existente = transaccionRepository.findById(id)
                .orElseThrow(() -> new TransaccionNotFoundException(id));

        if (!reservasRepository.existsById(request.getReservaId())) {
            throw new ReservasProyeccionNotFoundException(request.getReservaId());
        }

        if (!metodoPagoRepository.existsById(request.getMetodoId())) {
            throw new MetodoPagoNotFoundException(request.getMetodoId());
        }

        pagosMapper.updateEntity(request, existente);
        Transaccion updated = transaccionRepository.save(existente);
        return pagosMapper.toResponse(updated);
    }

    public void delete(Long id) {
        if (!transaccionRepository.existsById(id)) {
            throw new TransaccionNotFoundException(id);
        }
        transaccionRepository.deleteById(id);
    }
}
