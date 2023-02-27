package com.sistema.blog.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.blog.dto.ComentarioDTO;
import com.sistema.blog.servicio.ComentarioServicio;

@RestController
@RequestMapping("/api")
public class ComentarioControlador {
	
	@Autowired
	ComentarioServicio comentarioServicio;
	
	@PostMapping("/publicaciones/{publicacionId}/comentario")
	public ResponseEntity<ComentarioDTO> guardarComentario(@PathVariable(name = "publicacionId")long publicacionId,
			@RequestBody ComentarioDTO comentarioDTO){
		return new ResponseEntity<>(comentarioServicio.crearComentario(publicacionId, comentarioDTO),HttpStatus.CREATED);
	}

}
