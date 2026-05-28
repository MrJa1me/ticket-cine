package cl.ticketcine.usuarios.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Cliente Feign para comunicacion sincronica con ms-usuarios.
 */
@FeignClient(name = "ms-usuarios")
public interface UsuariosClient {

    @GetMapping("/api/v1/existe/{email}")
    Boolean existsByEmail(@PathVariable String email);
}
