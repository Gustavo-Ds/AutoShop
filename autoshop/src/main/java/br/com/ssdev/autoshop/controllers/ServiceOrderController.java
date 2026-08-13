package br.com.ssdev.autoshop.controllers;

import br.com.ssdev.autoshop.dto.ServiceOrderRequestDTO;
import br.com.ssdev.autoshop.dto.ServiceOrderResponseDTO;
import br.com.ssdev.autoshop.services.ServiceOrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/service-orders")
public class ServiceOrderController {

    @Autowired
    private ServiceOrderService serviceOrderService;

    @PostMapping
    public ResponseEntity<ServiceOrderResponseDTO> create(@RequestBody @Valid ServiceOrderRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(serviceOrderService.create(dto));
    }

    @GetMapping
    public ResponseEntity<Page<ServiceOrderResponseDTO>> getAll(@PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(serviceOrderService.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceOrderResponseDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(serviceOrderService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceOrderResponseDTO> update(
            @PathVariable UUID id,
            @RequestBody @Valid ServiceOrderRequestDTO dto
    ) {
        return ResponseEntity.ok(serviceOrderService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        serviceOrderService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
