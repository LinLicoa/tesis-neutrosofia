package com.application.webapi.service;

import com.application.webapi.service.dto.EvaluacionDTO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EvaluacionService {
    EvaluacionDTO create(EvaluacionDTO evaluacionDTO);

    Optional<EvaluacionDTO> findById(UUID id);

    List<EvaluacionDTO> findByPacienteId(UUID pacienteId);

    List<EvaluacionDTO> findByUsuarioId(UUID usuarioId);
}
