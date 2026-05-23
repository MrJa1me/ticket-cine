package cl.ticketcine.autenticacion.mapper;

import cl.ticketcine.autenticacion.dto.AuthResponse;
import cl.ticketcine.autenticacion.dto.RegisterRequest;
import cl.ticketcine.autenticacion.dto.UserResponse;
import cl.ticketcine.autenticacion.model.entity.Credencial;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * Mapper MapStruct para convertir entre Credencial (entidad) y DTOs.
 * Sigue el patron CSR: Controller -> Service -> Repository, con mapeo
 * limpio entre capas usando MapStruct.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuthMapper {

    /**
     * Convierte un RegisterRequest en una entidad Credencial.
     * El passHash se setea en el servicio, no aqui.
     */
    @Mapping(target = "passHash", ignore = true)
    @Mapping(target = "mfaHabilitado", ignore = true)
    @Mapping(target = "tokens", ignore = true)
    Credencial toEntity(RegisterRequest request);

    /**
     * Convierte una Credencial en AuthResponse (respuesta de login/register).
     */
    @Mapping(target = "token", ignore = true)
    @Mapping(target = "expiraAt", ignore = true)
    @Mapping(target = "userEmail", source = "userEmail")
    @Mapping(target = "role", source = "roles")
    AuthResponse toAuthResponse(Credencial credencial);

    /**
     * Convierte una Credencial en UserResponse (informacion publica del usuario).
     */
    @Mapping(target = "userEmail", source = "userEmail")
    @Mapping(target = "role", source = "roles")
    UserResponse toUserResponse(Credencial credencial);

    /**
     * Convierte una lista de Credenciales en lista de UserResponse.
     */
    List<UserResponse> toUserResponseList(List<Credencial> credenciales);
}
