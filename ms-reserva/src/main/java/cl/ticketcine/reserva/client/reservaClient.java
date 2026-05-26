package cl.ticketcine.reserva.client;

import cl.ticketcine.reserva.dto.reservaResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "ms-reserva", url = "http://localhost:9006/api/v1/reservas")
public interface reservaClient {

    @GetMapping("/{idAsiento}")
    reservaResponse findById(@PathVariable Integer idAsiento);

    @GetMapping("/sala/{idSala}")
    List<reservaResponse> findBySalaId(@PathVariable String idSala);
}
