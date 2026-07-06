package cl.ticketcine.compras.service;

import cl.ticketcine.compras.client.BoletoClient;
import cl.ticketcine.compras.client.PagoClient;
import cl.ticketcine.compras.dto.CompraResponse;
import cl.ticketcine.compras.dto.CrearPagoRequest;
import cl.ticketcine.compras.event.CompraEventProducer;
import cl.ticketcine.compras.mapper.CompraMapper;
import cl.ticketcine.compras.model.Compra;
import cl.ticketcine.compras.model.DetalleCompra;
import cl.ticketcine.compras.model.EstadoCompra;
import cl.ticketcine.compras.repository.CompraRepository;
import cl.ticketcine.common.event.CompraCreatedEvent;
import cl.ticketcine.common.event.CompraUpdatedEvent;
import cl.ticketcine.common.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompraService {

    private final CompraRepository compraRepository;
    private final BoletoClient boletoClient;
    private final PagoClient pagoClient;
    private final CompraEventProducer compraEventProducer;
    private final CompraMapper compraMapper;

    @Transactional
    public Compra guardar(Compra compra, String metodoPago) {
        int totalCompra = 0;

        if (compra.getDetalles() != null) {
            for (DetalleCompra detalle : compra.getDetalles()) {
                Integer precio = boletoClient.obtenerPrecioBoleto(detalle.getIdBoleto());

                int subtotal = precio * detalle.getCantidad();

                // Convertimos el subtotal a BigDecimal para que la entidad lo acepte
                detalle.setSubtotal(BigDecimal.valueOf(subtotal));
                detalle.setCompra(compra);

                totalCompra += subtotal;
            }
        }

        // Convertimos el totalCompra a BigDecimal para que la entidad lo acepte
        compra.setTotal(BigDecimal.valueOf(totalCompra));
        compra.setEstado(EstadoCompra.PENDIENTE);

        Compra guardada = compraRepository.save(compra);

        compraEventProducer.sendCreated(CompraCreatedEvent.builder()
                .idCompra(guardada.getIdCompra())
                .idUsuario(guardada.getIdUsuario())
                .total(guardada.getTotal())
                .estado(guardada.getEstado().name())
                .build());

        pagoClient.crearPago(CrearPagoRequest.builder()
                .idCompra(guardada.getIdCompra())
                .monto(guardada.getTotal())
                .metodo(metodoPago != null ? metodoPago : "WEBPAY")
                .build());

        return guardada;
    }

    @Transactional
    public CompraResponse confirmar(Integer id) {
        Compra compra = compraRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Compras", "ID", id.toString()));

        if (compra.getEstado() == EstadoCompra.COMPLETADA) {
            return compraMapper.toResponse(compra);
        }

        compra.setEstado(EstadoCompra.COMPLETADA);
        Compra guardada = compraRepository.save(compra);

        compraEventProducer.sendUpdated(CompraUpdatedEvent.builder()
                .idCompra(guardada.getIdCompra())
                .total(guardada.getTotal())
                .estado(guardada.getEstado().name())
                .build());

        return compraMapper.toResponse(guardada);
    }

    @Transactional(readOnly = true)
    public List<CompraResponse> listarTodas() {
        return compraRepository.findAllWithDetalles().stream()
                .map(compraMapper::toResponse)
                .toList();
    }
}
