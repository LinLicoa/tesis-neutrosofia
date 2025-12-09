package com.application.webapi.service.mapper;

import com.application.webapi.domain.entity.Paciente;
import com.application.webapi.service.dto.PacienteDTO;
import org.springframework.stereotype.Component;

@Component
public class PacienteMapper {

    public PacienteDTO toDto(Paciente entity) {
        if (entity == null)
            return null;
        PacienteDTO dto = new PacienteDTO();
        dto.setId(entity.getId());
        if (entity.getUsuario() != null) {
            dto.setUsuarioId(entity.getUsuario().getId());
        }
        dto.setCedula(entity.getCedula());
        dto.setNombreEncriptado(entity.getNombreEncriptado());
        dto.setEdad(entity.getEdad());
        dto.setGenero(entity.getGenero());
        dto.setEnfermedadCronica(entity.getEnfermedadCronica());
        dto.setActivo(entity.isActivo());
        dto.setFechaRegistro(entity.getFechaRegistro());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    public Paciente toEntity(PacienteDTO dto) {
        if (dto == null)
            return null;
        Paciente entity = new Paciente();
        entity.setId(dto.getId());
        entity.setCedula(dto.getCedula());
        entity.setNombreEncriptado(dto.getNombreEncriptado());
        entity.setEdad(dto.getEdad());
        entity.setGenero(dto.getGenero());
        entity.setEnfermedadCronica(dto.getEnfermedadCronica());
        entity.setActivo(dto.isActivo());
        // Medico setting should be done in service
        return entity;
    }
}
