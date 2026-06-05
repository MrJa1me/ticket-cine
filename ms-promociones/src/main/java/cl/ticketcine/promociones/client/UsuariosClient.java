package cl.ticketcine.promociones.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-usuarios")
public interface UsuariosClient {

    @GetMapping("/api/v1/usuarios/existe/{email}")
    boolean existsByEmail(@PathVariable("email") String email);
}
