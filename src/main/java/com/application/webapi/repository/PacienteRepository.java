package com.application.webapi.repository;

import com.application.webapi.domain.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, UUID> {
    Optional<Paciente> findByUsuarioIdAndCedula(UUID usuarioId, String cedula);

    List<Paciente> findByUsuarioId(UUID usuarioId);
}
