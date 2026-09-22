package com.app.Cursos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.Cursos.entity.Cursos;


public interface CursosRepository extends JpaRepository<Cursos, Integer>  {

}
