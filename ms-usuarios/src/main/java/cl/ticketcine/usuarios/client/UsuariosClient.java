package cl.ticketcine.usuarios.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Cliente Feign para comunicacion sincronica con ms-usuarios.
 */
@FeignClient(name = "ms-usuarios", url = "http://localhost:9002/api/v1/usuarios")
public interface UsuariosClient {

    @GetMapping("/existe/{email}")
    Boolean existsByEmail(@PathVariable String email);
}
