package com.application.webapi.service.impl;

import com.application.webapi.domain.entity.Usuario;
import com.application.webapi.repository.UsuarioRepository;
import com.application.webapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Usuario save(Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario createAdmin(Usuario usuario) {
        usuario.setRol("ADMIN");
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    @Override
    public Optional<Usuario> findById(UUID id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    private final com.application.webapi.repository.ParametroSistemaRepository parametroSistemaRepository;

    @Override
    public boolean existsByEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    @Override
    public void loginFailed(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElse(null);
        if (usuario == null)
            return;

        int maxIntentos = Integer.parseInt(parametroSistemaRepository.findByClave("MAX_INTENTOS_LOGIN")
                .map(p -> p.getValor())
                .orElse("3"));

        usuario.setIntentosFallidos(usuario.getIntentosFallidos() + 1);

        if (usuario.getIntentosFallidos() >= maxIntentos) {
            int minutosBloqueo = Integer.parseInt(parametroSistemaRepository.findByClave("TIEMPO_BLOQUEO_MINUTOS")
                    .map(p -> p.getValor())
                    .orElse("15"));
            usuario.setBloqueadoHasta(java.time.LocalDateTime.now().plusMinutes(minutosBloqueo));
        }
        usuarioRepository.save(usuario);
    }

    @Override
    public void loginSucceeded(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email).orElse(null);
        if (usuario == null)
            return;

        usuario.setIntentosFallidos(0);
        usuario.setBloqueadoHasta(null);
        usuario.setUltimoAcceso(java.time.LocalDateTime.now());
        usuarioRepository.save(usuario);
    }
}
