package cl.ticketcine.boletos.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import cl.ticketcine.boletos.dto.BoletoRequest;
import cl.ticketcine.boletos.dto.BoletoResponse;

import cl.ticketcine.boletos.event.BoletoEventProducer;
import cl.ticketcine.boletos.mapper.BoletoMapper;
import cl.ticketcine.boletos.model.Boleto;
import cl.ticketcine.boletos.repository.BoletoRepository;
import cl.ticketcine.common.event.BoletoCreatedEvent;
import cl.ticketcine.common.event.BoletoUpdatedEvent;
import cl.ticketcine.common.exception.*;

/**
 * Servicio encargado de aplicar las reglas de negocio de boletos:
 * - Gestiona operaciones CRUD, validaciones de negocio y de integridad.
 * - Valida que el código del boleto sea único en el sistema.
 * - Lanza excepciones de la librería común respetando el orden exacto de sus constructores.
 */
@Service
@RequiredArgsConstructor
public class BoletoService {

    private final BoletoRepository boletoRepository;
    // Se Declara el repositorio a necesitar
    private final BoletoMapper boletoMapper;
    private final BoletoEventProducer boletoEventProducer;

    public List<BoletoResponse> findAll() {
        return boletoMapper.toResponseList(boletoRepository.findAll());
    }

    public BoletoResponse findById(Integer id) {
        return boletoMapper.toResponse(getBoletoById(id));
    }

    @Transactional
    public Integer obtenerPrecio(Integer id) {
        Boleto boleto = getBoletoById(id);
        if (boleto.getZona() == null || boleto.getZona().getPrecioBase() == null) {
            throw new EntityNotFoundException("Boletos", "precio para ID", id.toString());
        }
        return boleto.getZona().getPrecioBase().intValue();
    }

    @Transactional
    // @Transactional asegura que si algo falla a la mitad, la base de datos deshace los cambios.
    public BoletoResponse create(BoletoRequest request) {
        validateCodigoUnico(request.getCodigo());

        Boleto boleto = new Boleto();
        boletoMapper.updateFromRequest(request, boleto);
        
        // Regla de negocio: La fecha de emisión se asigna de forma automática al crearse
        boleto.setFechaEmision(LocalDate.now());

        boletoRepository.save(boleto);

        boletoEventProducer.sendCreated(BoletoCreatedEvent.builder()
                .idBoleto(boleto.getIdBoleto())
                .idPelicula(boleto.getPelicula().getIdPelicula())
                .idZona(boleto.getZona().getIdZona())
                .codigo(boleto.getCodigo())
                .estado(boleto.getEstado())
                .build());

        return boletoMapper.toResponse(boleto);
    }

    @Transactional
    public BoletoResponse update(Integer id, BoletoRequest request) {
        // Si el código enviado es diferente al que ya tiene el boleto, validamos que el nuevo no esté duplicado
        if (!checkMismoCodigo(id, request.getCodigo())) {
            validateCodigoUnico(request.getCodigo());
        }

        Boleto boleto = getBoletoById(id);
        boletoMapper.updateFromRequest(request, boleto);
        boletoRepository.save(boleto);

        boletoEventProducer.sendUpdated(BoletoUpdatedEvent.builder()
                .idBoleto(boleto.getIdBoleto())
                .idPelicula(boleto.getPelicula().getIdPelicula())
                .codigo(boleto.getCodigo())
                .estado(boleto.getEstado())
                .build());

        return boletoMapper.toResponse(boleto);
    }

    @Transactional
    public void deleteById(Integer id) {
        Boleto boleto = getBoletoById(id);
        List<String> tablasAsociadas = new ArrayList<>();

        // Validación de integridad referencial local: No eliminar boletos con reservas vigentes
        if (boleto.getReservas() != null && !boleto.getReservas().isEmpty()) {
            tablasAsociadas.add("Reservas Activas");
        }

        // Si existen dependencias, lanzamos la excepción estructurada de integridad
        if (!tablasAsociadas.isEmpty()) {
            throw new ReferentialIntegrityException("Boletos", id.longValue(), String.join(", ", tablasAsociadas));
        }

        boletoRepository.delete(boleto);
    }

    /**
     * Valida que el código del boleto no esté duplicado en el sistema empleando las firmas del Common.
     */
    private void validateCodigoUnico(String codigo) {
        if (boletoRepository.existsByCodigo(codigo)) {
            // Se pasan los 4 parámetros: (Entidad, Campo, Valor Conflicto, Descripción del recurso)
            throw new DuplicateResourceException("Boletos", "Código", codigo, "El código ingresado ya está registrado para otro boleto.");
        }
    }

    private boolean checkMismoCodigo(Integer id, String codigo) {
        Boleto boleto = getBoletoById(id);
        return codigo.equalsIgnoreCase(boleto.getCodigo());
    }

    /**
     * Obtiene la entidad o lanza 404 (EntityNotFoundException) respetando el orden exacto de los parámetros: (Entidad, Llave, Valor).
     */
    private Boleto getBoletoById(Integer id) {
        Boleto boleto = boletoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Boletos", "ID", id.toString()));
        
        return Objects.requireNonNull(boleto);
    }
}