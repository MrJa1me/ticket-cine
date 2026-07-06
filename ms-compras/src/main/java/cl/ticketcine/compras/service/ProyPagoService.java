package cl.ticketcine.compras.service;

import cl.ticketcine.compras.model.ProyPago;
import cl.ticketcine.compras.repository.ProyPagoRepository;
import cl.ticketcine.common.event.PagoCreatedEvent;
import cl.ticketcine.common.event.PagoUpdatedEvent;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProyPagoService {

    private final ProyPagoRepository proyPagoRepository;

    @Transactional
    public void sincronizarCreado(PagoCreatedEvent event) {
        proyPagoRepository.save(ProyPago.builder()
                .idPago(event.getIdPago())
                .monto(event.getMonto())
                .estado(event.getEstado())
                .build());
    }

    @Transactional
    public void sincronizarActualizado(PagoUpdatedEvent event) {
        ProyPago proyPago = proyPagoRepository.findById(event.getIdPago())
                .orElse(ProyPago.builder().idPago(event.getIdPago()).build());
        proyPago.setMonto(event.getMonto());
        proyPago.setEstado(event.getEstado());
        proyPagoRepository.save(proyPago);
    }
}
