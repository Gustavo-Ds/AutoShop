package br.com.ssdev.autoshop.controllers;

import br.com.ssdev.autoshop.dto.VehicleRequestDTO;
import br.com.ssdev.autoshop.dto.VehicleResponseDTO;
import br.com.ssdev.autoshop.services.VehicleService;
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
@RequestMapping("/api/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @PostMapping
    public ResponseEntity<VehicleResponseDTO> create(@RequestBody @Valid VehicleRequestDTO vehicleRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vehicleService.create(vehicleRequestDTO));
    }

    @GetMapping
    public ResponseEntity<Page<VehicleResponseDTO>> getAll(@PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(vehicleService.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponseDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(vehicleService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleResponseDTO> update(
            @PathVariable UUID id,
            @RequestBody @Valid VehicleRequestDTO vehicleRequestDTO
    ) {
        return ResponseEntity.ok(vehicleService.update(id, vehicleRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        vehicleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
