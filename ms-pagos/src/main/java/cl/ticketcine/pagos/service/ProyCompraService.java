package cl.ticketcine.pagos.service;

import cl.ticketcine.common.event.CompraCreatedEvent;
import cl.ticketcine.common.event.CompraUpdatedEvent;
import cl.ticketcine.pagos.model.ProyCompra;
import cl.ticketcine.pagos.repository.ProyCompraRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProyCompraService {

    private final ProyCompraRepository proyCompraRepository;

    @Transactional
    public void sincronizarCreado(CompraCreatedEvent event) {
        proyCompraRepository.save(ProyCompra.builder()
                .idCompra(event.getIdCompra())
                .total(event.getTotal())
                .estado(event.getEstado())
                .build());
    }

    @Transactional
    public void sincronizarActualizado(CompraUpdatedEvent event) {
        ProyCompra proyCompra = proyCompraRepository.findById(event.getIdCompra())
                .orElse(ProyCompra.builder().idCompra(event.getIdCompra()).build());
        proyCompra.setTotal(event.getTotal());
        proyCompra.setEstado(event.getEstado());
        proyCompraRepository.save(proyCompra);
    }
}
