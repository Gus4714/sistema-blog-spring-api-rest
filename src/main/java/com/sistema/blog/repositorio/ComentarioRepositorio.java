package com.sistema.blog.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistema.blog.entidades.Comentario;

public interface ComentarioRepositorio extends JpaRepository<Comentario, Long> {

}
