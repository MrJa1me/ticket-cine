package cl.ticketcine.reserva.client;

import cl.ticketcine.reserva.dto.ReservaResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "ms-reserva", url = "http://localhost:9006/api/v1/reservas")
public interface ReservaClient {

    @GetMapping("/{idAsiento}")
    ReservaResponse findById(@PathVariable Integer idAsiento);

    @GetMapping("/sala/{idSala}")
    List<ReservaResponse> findBySalaId(@PathVariable String idSala);
}
