package cl.ticketcine.pagos.service;

import cl.ticketcine.common.event.PagoCreatedEvent;
import cl.ticketcine.common.event.PagoUpdatedEvent;
import cl.ticketcine.common.exception.EntityNotFoundException;
import cl.ticketcine.pagos.client.CompraClient;
import cl.ticketcine.pagos.dto.PagoResponse;
import cl.ticketcine.pagos.event.PagoEventProducer;
import cl.ticketcine.pagos.mapper.PagoMapper;
import cl.ticketcine.pagos.model.EstadoPago;
import cl.ticketcine.pagos.model.Pago;
import cl.ticketcine.pagos.repository.PagoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@SuppressWarnings("null")
public class PagoService {

    private final PagoRepository pagoRepository;
    private final PagoEventProducer pagoEventProducer;
    private final PagoMapper pagoMapper;
    private final CompraClient compraClient;

    @Transactional(readOnly = true)
    public List<PagoResponse> listarTodos() {
        return pagoRepository.findAllOrdered().stream()
                .map(pagoMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public Optional<PagoResponse> buscarPorId(Integer id) {
        return pagoRepository.findById(id).map(pagoMapper::toResponse);
    }

    @Transactional
    public PagoResponse guardar(Pago pago) {
        if (pago.getEstado() == null) {
            pago.setEstado(EstadoPago.PENDIENTE);
        }
        if (pago.getFechaPago() == null) {
            pago.setFechaPago(LocalDate.now());
        }

        Pago guardado = pagoRepository.save(pago);

        pagoEventProducer.sendCreated(PagoCreatedEvent.builder()
                .idPago(guardado.getIdPago())
                .monto(guardado.getMonto())
                .metodo(guardado.getMetodo() != null ? guardado.getMetodo().name() : null)
                .estado(guardado.getEstado().name())
                .build());

        return pagoMapper.toResponse(guardado);
    }

    @Transactional
    public PagoResponse aprobar(Integer id) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pagos", "ID", id.toString()));

        pago.setEstado(EstadoPago.APROBADO);
        Pago guardado = pagoRepository.save(pago);

        pagoEventProducer.sendUpdated(PagoUpdatedEvent.builder()
                .idPago(guardado.getIdPago())
                .monto(guardado.getMonto())
                .metodo(guardado.getMetodo() != null ? guardado.getMetodo().name() : null)
                .estado(guardado.getEstado().name())
                .build());

        if (guardado.getIdCompra() != null) {
            compraClient.confirmarCompra(guardado.getIdCompra());
        }

        return pagoMapper.toResponse(guardado);
    }

    @Transactional
    public void eliminar(Integer id) {
        pagoRepository.deleteById(id);
    }
}
