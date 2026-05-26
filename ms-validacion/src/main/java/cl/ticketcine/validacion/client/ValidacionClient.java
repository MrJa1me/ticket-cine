package cl.ticketcine.validacion.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Cliente Feign para comunicacion sincronica con ms-validacion.
 */
@FeignClient(name = "ms-validacion", url = "http://localhost:9009/api/v1/validacion")
public interface ValidacionClient {

    @GetMapping("/qr-codigos/exists/{email}")
    Boolean existsByEmail(@PathVariable String email);
}
