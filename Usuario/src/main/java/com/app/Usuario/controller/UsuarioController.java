
package com.app.Usuario.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.Usuario.dto.UsuarioDto;
import com.app.Usuario.dto.UsuarioRegistroDto;
import com.app.Usuario.service.UsuarioService;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/registro")
    public ResponseEntity<UsuarioDto> registrar(
            @RequestBody UsuarioRegistroDto registro) {

        UsuarioDto usuario = usuarioService.registrar(registro);

        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }

    @GetMapping("/usuarios")
    public ResponseEntity<List<UsuarioDto>> listar() {
        return ResponseEntity.ok(usuarioService.listar());
    }

    @GetMapping("/{nombreCompleto}")
    public ResponseEntity<UsuarioDto> obtenerPorNombreCompleto(
            @PathVariable String nombreCompleto) {

        UsuarioDto usuario =
                usuarioService.obtenerPorNombreCompleto(nombreCompleto);

        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(usuario);
    }
}
