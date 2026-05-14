package cl.ticketcine.salas.service;

import cl.ticketcine.salas.dto.SalaRequest;
import cl.ticketcine.salas.dto.SalaResponse;
import cl.ticketcine.salas.exception.SalaNotFoundException;
import cl.ticketcine.salas.mapper.SalaMapper;
import cl.ticketcine.salas.model.entity.Sala;
import cl.ticketcine.salas.repository.SalaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SalaServiceTest {

    @Mock
    private SalaRepository salaRepository;

    @Mock
    private SalaMapper salaMapper;

    @InjectMocks
    private SalaService salaService;

    private Sala sala;
    private SalaRequest request;
    private SalaResponse response;

    @BeforeEach
    void setUp() {
        sala = Sala.builder()
                .idSala("SALA1")
                .formato("IMAX")
                .capacidad(120)
                .build();

        request = new SalaRequest();
        request.setIdSala("SALA1");
        request.setFormato("IMAX");
        request.setCapacidad(120);

        response = new SalaResponse();
        response.setIdSala("SALA1");
        response.setFormato("IMAX");
        response.setCapacidad(120);
        response.setCantidadAsientos(0);
        response.setCantidadMantenimientos(0);
    }

    @Test
    void findById_whenSalaExists_returnsResponse() {
        when(salaRepository.findById("SALA1")).thenReturn(Optional.of(sala));
        when(salaMapper.toResponse(sala)).thenReturn(response);

        SalaResponse result = salaService.findById("SALA1");

        Assertions.assertNotNull(result);
        Assertions.assertEquals("SALA1", result.getIdSala());
    }

    @Test
    void findById_whenSalaMissing_throwsNotFound() {
        when(salaRepository.findById("SALA1")).thenReturn(Optional.empty());

        Assertions.assertThrows(SalaNotFoundException.class, () -> salaService.findById("SALA1"));
    }

    @Test
    void create_whenSalaExists_throwsIllegalArgument() {
        when(salaRepository.existsById("SALA1")).thenReturn(true);

        Assertions.assertThrows(IllegalArgumentException.class, () -> salaService.create(request));
    }

    @Test
    void create_whenSalaMissing_savesSala() {
        when(salaRepository.existsById("SALA1")).thenReturn(false);
        when(salaMapper.toEntity(request)).thenReturn(sala);
        when(salaRepository.save(any(Sala.class))).thenReturn(sala);
        when(salaMapper.toResponse(sala)).thenReturn(response);

        SalaResponse result = salaService.create(request);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("SALA1", result.getIdSala());
    }
}
