package cl.ticketcine.catalogo.service;

import cl.ticketcine.catalogo.dto.PeliculaRequestDTO;
import cl.ticketcine.catalogo.dto.PeliculaResponseDTO;
import cl.ticketcine.catalogo.event.PeliculaEventProducer;
import cl.ticketcine.catalogo.mapper.PeliculaMapper;
import cl.ticketcine.catalogo.model.Pelicula;
import cl.ticketcine.catalogo.repository.PeliculaRepository;
import cl.ticketcine.common.event.PeliculaCreatedEvent;
import cl.ticketcine.common.event.PeliculaDeletedEvent;
import cl.ticketcine.common.event.PeliculaUpdatedEvent;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias para {@link PeliculaService} en TicketCine.
 *
 * Mockito aísla el servicio de repositorio, mapper y productor Kafka.
 */
@ExtendWith(MockitoExtension.class)
class PeliculaServiceTest {

    @Mock
    private PeliculaRepository repository;

    @Mock
    private PeliculaMapper mapper;

    @Mock
    private PeliculaEventProducer peliculaEventProducer;

    @InjectMocks
    private PeliculaService peliculaService;

    private final Faker faker = new Faker();

    @BeforeEach
    void setUp() {
        lenient().when(mapper.toResponseDTO(any(Pelicula.class))).thenAnswer(invocation -> {
            Pelicula pelicula = invocation.getArgument(0);
            if (pelicula == null) {
                return null;
            }
            PeliculaResponseDTO response = new PeliculaResponseDTO();
            response.setIdPelicula(pelicula.getIdPelicula());
            response.setNombrePelicula(pelicula.getNombrePelicula());
            response.setCategoria(pelicula.getCategoria());
            response.setFecha(pelicula.getFecha());
            response.setEstado(pelicula.getEstado());
            response.setIdioma(pelicula.getIdioma());
            response.setDuracionMinutos(pelicula.getDuracionMinutos());
            response.setClasificacion(pelicula.getClasificacion());
            response.setSinopsis(pelicula.getSinopsis());
            return response;
        });

        lenient().when(mapper.toEntity(any(PeliculaRequestDTO.class))).thenAnswer(invocation -> {
            PeliculaRequestDTO request = invocation.getArgument(0);
            return Pelicula.builder()
                    .nombrePelicula(request.getNombrePelicula())
                    .categoria(request.getCategoria())
                    .fecha(request.getFecha())
                    .estado(request.getEstado())
                    .idioma(request.getIdioma())
                    .duracionMinutos(request.getDuracionMinutos())
                    .clasificacion(request.getClasificacion())
                    .sinopsis(request.getSinopsis())
                    .idGenero(request.getIdGenero())
                    .build();
        });

        lenient().doAnswer(invocation -> {
            PeliculaRequestDTO request = invocation.getArgument(0);
            Pelicula pelicula = invocation.getArgument(1);
            if (request != null && pelicula != null) {
                pelicula.setNombrePelicula(request.getNombrePelicula());
                pelicula.setCategoria(request.getCategoria());
                pelicula.setFecha(request.getFecha());
                pelicula.setEstado(request.getEstado());
                pelicula.setIdioma(request.getIdioma());
                pelicula.setDuracionMinutos(request.getDuracionMinutos());
                pelicula.setClasificacion(request.getClasificacion());
                pelicula.setSinopsis(request.getSinopsis());
                pelicula.setIdGenero(request.getIdGenero());
            }
            return null;
        }).when(mapper).updateFromRequest(any(PeliculaRequestDTO.class), any(Pelicula.class));
    }

    private Pelicula crearPeliculaSimulada(Integer id) {
        return Pelicula.builder()
                .idPelicula(id)
                .nombrePelicula(faker.movie().name())
                .categoria("Acción")
                .fecha(LocalDate.of(2026, faker.number().numberBetween(1, 12), faker.number().numberBetween(1, 28)))
                .estado("En cartelera")
                .idioma("Español")
                .duracionMinutos(faker.number().numberBetween(90, 180))
                .clasificacion("PG-13")
                .sinopsis(faker.lorem().sentence(12))
                .idGenero(faker.number().numberBetween(1, 10))
                .build();
    }

    private PeliculaRequestDTO crearPeliculaRequestSimulado() {
        return PeliculaRequestDTO.builder()
                .nombrePelicula("Dune: Parte Tres")
                .categoria("Ciencia ficción")
                .fecha(LocalDate.of(2026, 12, 18))
                .estado("Próximamente")
                .idioma("Español")
                .duracionMinutos(165)
                .clasificacion("PG-13")
                .sinopsis("Continuación de la saga en el desierto de Arrakis.")
                .idGenero(3)
                .build();
    }

    @Test
    void obtenerTodos_DeberiaRetornarLista_CuandoExistenRegistros() {
        Pelicula pelicula1 = crearPeliculaSimulada(1);
        Pelicula pelicula2 = crearPeliculaSimulada(2);

        when(repository.findAll()).thenReturn(List.of(pelicula1, pelicula2));

        List<PeliculaResponseDTO> resultado = peliculaService.obtenerTodos();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals(pelicula1.getIdPelicula(), resultado.get(0).getIdPelicula());
        assertEquals(pelicula1.getNombrePelicula(), resultado.get(0).getNombrePelicula());

        verify(repository).findAll();
        verify(mapper, times(2)).toResponseDTO(any(Pelicula.class));
    }

    @Test
    void buscarPorId_DeberiaRetornarPelicula_CuandoIdExiste() {
        Integer id = 10;
        Pelicula pelicula = crearPeliculaSimulada(id);

        when(repository.findById(id)).thenReturn(Optional.of(pelicula));

        PeliculaResponseDTO resultado = peliculaService.buscarPorId(id);

        assertNotNull(resultado);
        assertEquals(id, resultado.getIdPelicula());
        assertEquals(pelicula.getNombrePelicula(), resultado.getNombrePelicula());

        verify(repository).findById(id);
        verify(mapper).toResponseDTO(pelicula);
    }

    @Test
    void buscarPorId_DeberiaLanzarRuntimeException_CuandoIdNoExiste() {
        Integer id = 999;

        when(repository.findById(id)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> peliculaService.buscarPorId(id));
        assertTrue(ex.getMessage().contains("no existe"));

        verify(repository).findById(id);
        verify(mapper, never()).toResponseDTO(any());
    }

    @Test
    void crear_DeberiaCrearPeliculaYEnviarEvento_CuandoRequestEsValido() {
        PeliculaRequestDTO request = crearPeliculaRequestSimulado();

        when(repository.existsByNombrePeliculaIgnoreCaseAndFecha(request.getNombrePelicula(), request.getFecha()))
                .thenReturn(false);
        when(repository.save(any(Pelicula.class))).thenAnswer(invocation -> {
            Pelicula pelicula = invocation.getArgument(0);
            pelicula.setIdPelicula(100);
            return pelicula;
        });

        PeliculaResponseDTO resultado = peliculaService.crear(request);

        assertNotNull(resultado);
        assertEquals(100, resultado.getIdPelicula());
        assertEquals(request.getNombrePelicula(), resultado.getNombrePelicula());
        assertEquals(request.getDuracionMinutos(), resultado.getDuracionMinutos());

        verify(repository).existsByNombrePeliculaIgnoreCaseAndFecha(request.getNombrePelicula(), request.getFecha());
        verify(repository).save(any(Pelicula.class));
        verify(peliculaEventProducer).sendCreated(any(PeliculaCreatedEvent.class));
    }

    @Test
    void crear_DeberiaLanzarIllegalArgumentException_CuandoNombreYFechaYaExisten() {
        PeliculaRequestDTO request = crearPeliculaRequestSimulado();

        when(repository.existsByNombrePeliculaIgnoreCaseAndFecha(request.getNombrePelicula(), request.getFecha()))
                .thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> peliculaService.crear(request));

        verify(repository).existsByNombrePeliculaIgnoreCaseAndFecha(request.getNombrePelicula(), request.getFecha());
        verify(repository, never()).save(any(Pelicula.class));
        verify(peliculaEventProducer, never()).sendCreated(any());
    }

    @Test
    void actualizar_DeberiaActualizarPeliculaYEnviarEvento_CuandoIdExiste() {
        Integer id = 5;
        Pelicula peliculaExistente = crearPeliculaSimulada(id);
        PeliculaRequestDTO request = crearPeliculaRequestSimulado();

        when(repository.findById(id)).thenReturn(Optional.of(peliculaExistente));
        when(repository.existsByNombrePeliculaIgnoreCaseAndFechaAndIdPeliculaNot(
                request.getNombrePelicula(), request.getFecha(), id)).thenReturn(false);
        when(repository.save(any(Pelicula.class))).thenAnswer(invocation -> invocation.getArgument(0));

        PeliculaResponseDTO resultado = peliculaService.actualizar(id, request);

        assertNotNull(resultado);
        assertEquals(request.getNombrePelicula(), resultado.getNombrePelicula());
        assertEquals(request.getCategoria(), resultado.getCategoria());
        assertEquals(request.getClasificacion(), resultado.getClasificacion());

        verify(repository).findById(id);
        verify(repository).save(any(Pelicula.class));
        verify(peliculaEventProducer).sendUpdated(any(PeliculaUpdatedEvent.class));
    }

    @Test
    void actualizar_DeberiaLanzarRuntimeException_CuandoIdNoExiste() {
        Integer id = 999;
        PeliculaRequestDTO request = crearPeliculaRequestSimulado();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> peliculaService.actualizar(id, request));

        verify(repository).findById(id);
        verify(repository, never()).save(any(Pelicula.class));
        verify(peliculaEventProducer, never()).sendUpdated(any());
    }

    @Test
    void actualizar_DeberiaLanzarIllegalArgumentException_CuandoOtraPeliculaTieneMismoNombreYFecha() {
        Integer id = 5;
        Pelicula peliculaExistente = crearPeliculaSimulada(id);
        PeliculaRequestDTO request = crearPeliculaRequestSimulado();

        when(repository.findById(id)).thenReturn(Optional.of(peliculaExistente));
        when(repository.existsByNombrePeliculaIgnoreCaseAndFechaAndIdPeliculaNot(
                request.getNombrePelicula(), request.getFecha(), id)).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> peliculaService.actualizar(id, request));

        verify(repository).findById(id);
        verify(repository, never()).save(any(Pelicula.class));
        verify(peliculaEventProducer, never()).sendUpdated(any());
    }

    @Test
    void eliminar_DeberiaEliminarPeliculaYEnviarEvento_CuandoIdExiste() {
        Integer id = 15;

        when(repository.existsById(id)).thenReturn(true);

        peliculaService.eliminar(id);

        verify(repository).existsById(id);
        verify(repository).deleteById(id);
        verify(peliculaEventProducer).sendDeleted(any(PeliculaDeletedEvent.class));
    }

    @Test
    void eliminar_DeberiaLanzarRuntimeException_CuandoIdNoExiste() {
        Integer id = 999;

        when(repository.existsById(id)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> peliculaService.eliminar(id));

        verify(repository).existsById(id);
        verify(repository, never()).deleteById(anyInt());
        verify(peliculaEventProducer, never()).sendDeleted(any());
    }

    @Test
    void existePorId_DeberiaRetornarTrue_CuandoLaPeliculaExiste() {
        Integer idPelicula = 1;

        when(repository.existsById(idPelicula)).thenReturn(true);

        assertTrue(peliculaService.existePorId(idPelicula));

        verify(repository).existsById(idPelicula);
    }

    @Test
    void existePorId_DeberiaRetornarFalse_CuandoLaPeliculaNoExiste() {
        Integer idPelicula = 99;

        when(repository.existsById(idPelicula)).thenReturn(false);

        assertFalse(peliculaService.existePorId(idPelicula));

        verify(repository).existsById(idPelicula);
    }
}

// mvn test -Dtest=PeliculaServiceTest  (ejecutar dentro de ms-catalogo)
