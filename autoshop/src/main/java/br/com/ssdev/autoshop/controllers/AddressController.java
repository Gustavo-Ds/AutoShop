package br.com.ssdev.autoshop.controllers;

import br.com.ssdev.autoshop.dto.AddressRequestDTO;
import br.com.ssdev.autoshop.dto.AddressResponseDTO;
import br.com.ssdev.autoshop.services.AddressService;
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
@RequestMapping("/api/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping
    public ResponseEntity<AddressResponseDTO> create(@RequestBody @Valid AddressRequestDTO addressRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addressService.create(addressRequestDTO));
    }

    @GetMapping
    public ResponseEntity<Page<AddressResponseDTO>> getAll(@PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(addressService.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressResponseDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(addressService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressResponseDTO> update(
            @PathVariable UUID id,
            @RequestBody @Valid AddressRequestDTO addressRequestDTO
    ) {
        return ResponseEntity.ok(addressService.update(id, addressRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        addressService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
