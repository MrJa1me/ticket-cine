package cl.ticketcine.compras.client;

import cl.ticketcine.compras.dto.CrearPagoRequest;
import cl.ticketcine.compras.dto.CrearPagoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ms-pagos")
public interface PagoClient {

    @PostMapping("/api/v1/pagos")
    CrearPagoResponse crearPago(@RequestBody CrearPagoRequest request);
}
