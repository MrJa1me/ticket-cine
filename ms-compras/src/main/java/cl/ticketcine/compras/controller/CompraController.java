package cl.ticketcine.compras.controller;

import cl.ticketcine.compras.dto.CompraRequest;
import cl.ticketcine.compras.dto.CompraResponse;
import cl.ticketcine.compras.mapper.CompraMapper;
import cl.ticketcine.compras.model.Compra;
import cl.ticketcine.compras.service.CompraService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/compras")
@RequiredArgsConstructor

public class CompraController {

    private final CompraService compraService;
    private final CompraMapper compraMapper;

    @PostMapping
    public ResponseEntity<CompraResponse> crearCompra(@RequestBody CompraRequest request) {
        Compra compraACrear = compraMapper.toEntity(request);
        Compra compraGuardada = compraService.guardar(compraACrear, request.getMetodoPago());
        return ResponseEntity.status(HttpStatus.CREATED).body(compraMapper.toResponse(compraGuardada));
    }

    @PutMapping("/confirmar/id/{id}")
    public ResponseEntity<CompraResponse> confirmarCompra(@PathVariable Integer id) {
        return ResponseEntity.ok(compraService.confirmar(id));
    }

    @GetMapping
    public ResponseEntity<List<CompraResponse>> listarCompras() {
        return ResponseEntity.ok(compraService.listarTodas());
    }

}
