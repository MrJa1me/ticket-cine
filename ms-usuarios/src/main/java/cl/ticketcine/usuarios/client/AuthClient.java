package cl.ticketcine.usuarios.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Cliente Feign para comunicacion sincronica con ms-autenticacion.
 */
@FeignClient(name = "ms-autenticacion", url = "http://localhost:9001/api/v1/auth")
public interface AuthClient {

    @GetMapping("/existe/{email}")
    Boolean existsByEmail(@PathVariable String email);
}
