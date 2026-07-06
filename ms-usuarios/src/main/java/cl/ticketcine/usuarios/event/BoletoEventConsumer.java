package cl.ticketcine.usuarios.event;

import cl.ticketcine.common.event.BoletoCreatedEvent;
import cl.ticketcine.common.event.BoletoUpdatedEvent;
import cl.ticketcine.usuarios.service.ProyBoletoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BoletoEventConsumer {

    private final ProyBoletoService proyBoletoService;

    @KafkaListener(
        topics = "boletos.boleto.created",
        groupId = "ms-usuarios",
        properties = {"spring.json.value.default.type=cl.ticketcine.common.event.BoletoCreatedEvent"}
    )
    public void onBoletoCreated(BoletoCreatedEvent event) {
        log.debug("Pelicula recibido → boleto created, idBoleto: {}", event.getIdBoleto());
        proyBoletoService.sincronizarCreado(event);
    }

    @KafkaListener(
        topics = "boletos.boleto.updated",
        groupId = "ms-usuarios",
        properties = {"spring.json.value.default.type=cl.ticketcine.common.event.BoletoUpdatedEvent"}
    )
    public void onBoletoUpdated(BoletoUpdatedEvent event) {
        log.debug("Pelicula recibido → boleto updated, idBoleto: {}", event.getIdBoleto());
        proyBoletoService.sincronizarActualizado(event);
    }
}
