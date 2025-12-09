package com.application.webapi.controller;

import com.application.webapi.service.EvaluacionService;
import com.application.webapi.service.dto.EvaluacionDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/evaluaciones")
@RequiredArgsConstructor
public class EvaluacionController {

    private final EvaluacionService evaluacionService;

    @PostMapping
    public ResponseEntity<EvaluacionDTO> create(@Valid @RequestBody EvaluacionDTO evaluacionDTO) {
        return ResponseEntity.ok(evaluacionService.create(evaluacionDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EvaluacionDTO> getById(@PathVariable UUID id) {
        return evaluacionService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<EvaluacionDTO>> getByPacienteId(@PathVariable UUID pacienteId) {
        return ResponseEntity.ok(evaluacionService.findByPacienteId(pacienteId));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<EvaluacionDTO>> getByUsuarioId(@PathVariable UUID usuarioId) {
        return ResponseEntity.ok(evaluacionService.findByUsuarioId(usuarioId));
    }
}
