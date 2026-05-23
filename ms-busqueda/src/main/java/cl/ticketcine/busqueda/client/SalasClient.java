package cl.ticketcine.busqueda.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Cliente Feign para comunicacion sincronica con ms-salas.
 */
@FeignClient(name = "ms-salas", url = "http://localhost:9005/api/v1/salas")
public interface SalasClient {

    @GetMapping("/{id}")
    Object findSalaById(@PathVariable String id);
}
