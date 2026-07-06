package cl.ticketcine.usuarios.service;

import cl.ticketcine.common.event.BoletoCreatedEvent;
import cl.ticketcine.common.event.BoletoUpdatedEvent;
import cl.ticketcine.usuarios.model.ProyBoleto;
import cl.ticketcine.usuarios.repository.ProyBoletoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProyBoletoService {

    private final ProyBoletoRepository proyBoletoRepository;

    @Transactional
    public void sincronizarCreado(BoletoCreatedEvent event) {
        proyBoletoRepository.save(ProyBoleto.builder()
                .idBoleto(event.getIdBoleto())
                .idPelicula(event.getIdPelicula())
                .idZona(event.getIdZona())
                .codigo(event.getCodigo())
                .estado(event.getEstado())
                .build());
    }

    @Transactional
    public void sincronizarActualizado(BoletoUpdatedEvent event) {
        ProyBoleto proyBoleto = proyBoletoRepository.findById(event.getIdBoleto())
                .orElse(ProyBoleto.builder().idBoleto(event.getIdBoleto()).build());
        proyBoleto.setCodigo(event.getCodigo());
        proyBoleto.setEstado(event.getEstado());
        if (event.getIdPelicula() != null) {
            proyBoleto.setIdPelicula(event.getIdPelicula());
        }
        proyBoletoRepository.save(proyBoleto);
    }
}
