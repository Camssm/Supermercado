
package com.app.Usuario.mapper;

import org.springframework.stereotype.Component;

import com.app.Usuario.dto.UsuarioDto;
import com.app.Usuario.entity.Usuario;

@Component
public class UsuarioMapper {

    public UsuarioDto toDto(Usuario usuario) {
        if (usuario == null) {
            return null;
        }

        return new UsuarioDto(
            usuario.getId(),
            usuario.getNombreCompleto(),
            usuario.getEmail(),
            usuario.getRol()
        );
    }
}
