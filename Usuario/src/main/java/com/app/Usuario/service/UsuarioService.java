
package com.app.Usuario.service;

import java.util.List;

import com.app.Usuario.dto.UsuarioDto;
import com.app.Usuario.dto.UsuarioRegistroDto;

public interface UsuarioService {

    UsuarioDto registrar(UsuarioRegistroDto registro);

    List<UsuarioDto> listar();

    UsuarioDto obtenerPorNombreCompleto(String nombreCompleto);
}
