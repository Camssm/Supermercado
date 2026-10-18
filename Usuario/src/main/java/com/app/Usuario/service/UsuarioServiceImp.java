
package com.app.Usuario.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.Usuario.dto.UsuarioDto;
import com.app.Usuario.dto.UsuarioLoginDto;
import com.app.Usuario.dto.UsuarioRegistroDto;
import com.app.Usuario.entity.Usuario;
import com.app.Usuario.enums.Rol;
import com.app.Usuario.mapper.UsuarioMapper;
import com.app.Usuario.repository.UsuarioRepository;

@Service
public class UsuarioServiceImp implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImp(
            UsuarioRepository usuarioRepository,
            UsuarioMapper usuarioMapper,
            PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UsuarioDto registrar(UsuarioRegistroDto registro) {

        if (registro.getNombreCompleto() == null
                || registro.getNombreCompleto().isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre completo es obligatorio");
        }

        if (registro.getEmail() == null
                || registro.getEmail().isBlank()) {
            throw new IllegalArgumentException(
                    "El email es obligatorio");
        }

        if (registro.getPassword() == null
                || registro.getPassword().isBlank()) {
            throw new IllegalArgumentException(
                    "La contraseña es obligatoria");
        }

        if (usuarioRepository.existsByEmail(registro.getEmail())) {
            throw new IllegalArgumentException(
                    "Ese email ya está registrado");
        }

        if (usuarioRepository.findByNombreCompleto(
                registro.getNombreCompleto()).isPresent()) {
            throw new IllegalArgumentException(
                    "Ese nombre completo ya está registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNombreCompleto(registro.getNombreCompleto().trim());
        usuario.setEmail(registro.getEmail().trim());
        usuario.setPassword(
                passwordEncoder.encode(registro.getPassword()));
        usuario.setRol(Rol.CLIENTE);

        Usuario guardado = usuarioRepository.save(usuario);

        return usuarioMapper.toDto(guardado);
    }

    @Override
    public List<UsuarioDto> listar() {
        return usuarioRepository.findAll()
                .stream()
                .map(usuarioMapper::toDto)
                .toList();
    }

    @Override
    public UsuarioDto obtenerPorNombreCompleto(String nombreCompleto) {
        return usuarioRepository.findByNombreCompleto(nombreCompleto)
                .map(usuarioMapper::toDto)
                .orElse(null);
    }
    
    @Override
    public UsuarioDto validarCredenciales(UsuarioLoginDto login) {
        if (login == null
                || login.getNombreCompleto() == null
                || login.getPassword() == null) {
            return null;
        }

        Usuario usuario = usuarioRepository
                .findByNombreCompleto(login.getNombreCompleto())
                .orElse(null);

        if (usuario == null) {
            return null;
        }

        if (!passwordEncoder.matches(
                login.getPassword(),
                usuario.getPassword())) {
            return null;
        }

        return usuarioMapper.toDto(usuario);
    }
}
