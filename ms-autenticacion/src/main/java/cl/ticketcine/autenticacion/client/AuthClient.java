package cl.ticketcine.autenticacion.client;

import cl.ticketcine.autenticacion.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Cliente Feign para comunicacion sincronica con el microservicio de autenticacion.
 *
 * Permite a ms-autenticacion consultar datos de usuarios desde ms-usuarios
 * sin necesidad de compartir base de datos (autonomia de datos).
 */
@FeignClient(name = "ms-autenticacion", url = "http://localhost:9001/api/v1/autenticacion")
public interface AuthClient {

    /**
     * Obtiene un usuario por su email desde ms-usuarios.
     *
     * @param email email del usuario a consultar
     * @return UserResponse con los datos del usuario
     */
    @GetMapping("/auth/user/{email}")
    UserResponse findByEmail(@PathVariable("email") String email);
}
