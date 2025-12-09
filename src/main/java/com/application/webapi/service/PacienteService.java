package com.application.webapi.service;

import com.application.webapi.service.dto.PacienteDTO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PacienteService {
    PacienteDTO create(PacienteDTO pacienteDTO);

    Optional<PacienteDTO> findById(UUID id);

    List<PacienteDTO> findByUsuarioId(UUID usuarioId);

    void delete(UUID id);
}
