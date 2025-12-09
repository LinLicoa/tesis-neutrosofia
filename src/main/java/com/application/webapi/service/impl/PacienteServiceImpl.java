package com.application.webapi.service.impl;

import com.application.webapi.domain.entity.Paciente;
import com.application.webapi.domain.entity.Usuario;
import com.application.webapi.repository.PacienteRepository;
import com.application.webapi.repository.UsuarioRepository;
import com.application.webapi.service.PacienteService;
import com.application.webapi.service.dto.PacienteDTO;
import com.application.webapi.service.mapper.PacienteMapper;
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
public class PacienteServiceImpl implements PacienteService {

    private final PacienteRepository pacienteRepository;
    private final UsuarioRepository usuarioRepository;
    private final PacienteMapper pacienteMapper;

    @Override
    public PacienteDTO create(PacienteDTO pacienteDTO) {
        Usuario usuario = usuarioRepository.findById(pacienteDTO.getUsuarioId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        Paciente entity = pacienteMapper.toEntity(pacienteDTO);
        entity.setUsuario(usuario);

        entity = pacienteRepository.save(entity);
        return pacienteMapper.toDto(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PacienteDTO> findById(UUID id) {
        return pacienteRepository.findById(id)
                .map(pacienteMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PacienteDTO> findByUsuarioId(UUID usuarioId) {
        return pacienteRepository.findByUsuarioId(usuarioId).stream()
                .map(pacienteMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(UUID id) {
        pacienteRepository.deleteById(id);
    }
}
