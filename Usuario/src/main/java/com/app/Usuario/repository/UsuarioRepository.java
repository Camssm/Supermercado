package com.app.Usuario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.Usuario.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    boolean existsByEmail(String email);
}