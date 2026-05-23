package cl.ticketcine.autenticacion.client;

import cl.ticketcine.autenticacion.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Cliente Feign para comunicacion sincronica con el microservicio de usuarios.
 *
 * Permite a ms-autenticacion consultar datos de usuarios desde ms-usuarios
 * sin necesidad de compartir base de datos (autonomia de datos).
 */
@FeignClient(name = "ms-usuarios", url = "http://localhost:9002/api/v1/usuarios")
public interface UsuarioFeignClient {

    /**
     * Obtiene un usuario por su email desde ms-usuarios.
     *
     * @param email email del usuario a consultar
     * @return UserResponse con los datos del usuario
     */
    @GetMapping("/email/{email}")
    UserResponse findByEmail(@PathVariable("email") String email);
}
