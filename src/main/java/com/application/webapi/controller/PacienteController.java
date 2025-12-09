package com.application.webapi.controller;

import com.application.webapi.service.PacienteService;
import com.application.webapi.service.dto.PacienteDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/pacientes")
@RequiredArgsConstructor
public class PacienteController {

    private final PacienteService pacienteService;

    @PostMapping
    public ResponseEntity<PacienteDTO> create(@Valid @RequestBody PacienteDTO pacienteDTO) {
        return ResponseEntity.ok(pacienteService.create(pacienteDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteDTO> getById(@PathVariable UUID id) {
        return pacienteService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<PacienteDTO>> getByUsuarioId(@PathVariable UUID usuarioId) {
        return ResponseEntity.ok(pacienteService.findByUsuarioId(usuarioId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        pacienteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
