package cl.ticketcine.pagos.event;

import cl.ticketcine.common.event.CompraCreatedEvent;
import cl.ticketcine.common.event.CompraUpdatedEvent;
import cl.ticketcine.pagos.service.ProyCompraService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CompraEventConsumer {

    private final ProyCompraService proyCompraService;

    @KafkaListener(
        topics = "compras.compra.created",
        groupId = "ms-pagos",
        properties = {"spring.json.value.default.type=cl.ticketcine.common.event.CompraCreatedEvent"}
    )
    public void onCompraCreated(CompraCreatedEvent event) {
        log.debug("Pelicula recibido → compra created, idCompra: {}", event.getIdCompra());
        proyCompraService.sincronizarCreado(event);
    }

    @KafkaListener(
        topics = "compras.compra.updated",
        groupId = "ms-pagos",
        properties = {"spring.json.value.default.type=cl.ticketcine.common.event.CompraUpdatedEvent"}
    )
    public void onCompraUpdated(CompraUpdatedEvent event) {
        log.debug("Pelicula recibido → compra updated, idCompra: {}", event.getIdCompra());
        proyCompraService.sincronizarActualizado(event);
    }
}
