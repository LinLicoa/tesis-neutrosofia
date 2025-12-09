package com.application.webapi.service.impl;

import com.application.webapi.domain.entity.Evaluacion;
import com.application.webapi.domain.entity.Paciente;
import com.application.webapi.domain.entity.Usuario;
import com.application.webapi.repository.EvaluacionRepository;
import com.application.webapi.repository.PacienteRepository;
import com.application.webapi.repository.UsuarioRepository;
import com.application.webapi.service.EvaluacionService;
import com.application.webapi.service.dto.EvaluacionDTO;
import com.application.webapi.service.mapper.EvaluacionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class EvaluacionServiceImpl implements EvaluacionService {

    private final EvaluacionRepository evaluacionRepository;
    private final PacienteRepository pacienteRepository;
    private final UsuarioRepository usuarioRepository;
    private final EvaluacionMapper evaluacionMapper;

    @Override
    public EvaluacionDTO create(EvaluacionDTO evaluacionDTO) {
        Paciente paciente = pacienteRepository.findById(evaluacionDTO.getPacienteId())
                .orElseThrow(() -> new IllegalArgumentException("Paciente not found"));
        Usuario usuario = usuarioRepository.findById(evaluacionDTO.getUsuarioId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        Evaluacion entity = evaluacionMapper.toEntity(evaluacionDTO);
        entity.setPaciente(paciente);
        entity.setUsuario(usuario);

        entity = evaluacionRepository.save(entity);
        return evaluacionMapper.toDto(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EvaluacionDTO> findById(UUID id) {
        return evaluacionRepository.findById(id)
                .map(evaluacionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EvaluacionDTO> findByPacienteId(UUID pacienteId) {
        return evaluacionRepository.findByPacienteId(pacienteId).stream()
                .map(evaluacionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<EvaluacionDTO> findByUsuarioId(UUID usuarioId) {
        return evaluacionRepository.findByUsuarioId(usuarioId).stream()
                .map(evaluacionMapper::toDto)
                .collect(Collectors.toList());
    }
}
