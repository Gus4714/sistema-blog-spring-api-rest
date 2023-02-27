package com.sistema.blog.servicio;

import com.sistema.blog.dto.ComentarioDTO;

public interface ComentarioServicio {

	public ComentarioDTO crearComentario(long publicacionId,ComentarioDTO comentarioDTO);
}
