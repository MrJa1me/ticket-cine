package cl.ticketcine.busqueda.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Cliente Feign para comunicacion sincronica con ms-busqueda.
 */
@FeignClient(name = "ms-busqueda", url = "http://localhost:9003/api/v1/busqueda")
public interface BusquedaClient {

    @GetMapping("/{id}")
    Object findSalaById(@PathVariable String id);
}
