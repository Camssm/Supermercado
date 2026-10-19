
package com.app.Usuario.service;

import java.util.List;

import com.app.Usuario.dto.UsuarioDto;
import com.app.Usuario.dto.UsuarioRegistroDto;
import com.app.Usuario.dto.UsuarioLoginDto;

public interface UsuarioService {

    UsuarioDto registrar(UsuarioRegistroDto registro);

    List<UsuarioDto> listar();

    UsuarioDto obtenerPorNombreCompleto(String nombreCompleto);

    UsuarioDto validarCredenciales(UsuarioLoginDto login);
}
