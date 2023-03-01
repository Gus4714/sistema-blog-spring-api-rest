package com.sistema.blog.controlador;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	
	@GetMapping("/publicaciones/{publicacionId}/comentario")
	public List<ComentarioDTO> obtenerComentarioPorPublicacionID(@PathVariable(name = "publicacionId")Long publicacionId){
		return comentarioServicio.obtenerComentarioPorPublicacionId(publicacionId);
	}
	
	@GetMapping("/publicaciones/{publicacionId}/comentario/{id}")
	public ResponseEntity<ComentarioDTO> obtenerComentarioPorId(@PathVariable(name = "publicacionId")Long publicacionId,
			@PathVariable(name = "id")Long comentarioId){
		ComentarioDTO comentarioDTO = comentarioServicio.obtenerComentarioPorId(publicacionId, comentarioId);
		return new ResponseEntity<>(comentarioDTO,HttpStatus.OK);
	}
	
	@PutMapping("/publicaciones/{publicacionId}/comentario/{id}")
	public ResponseEntity<ComentarioDTO> actualizarComentario(@PathVariable(name = "publicacionId")Long publicacionId,
			@PathVariable(name = "id")Long comentarioId,@Valid @RequestBody ComentarioDTO comentarioDTO){
		ComentarioDTO comentarioActualizado = comentarioServicio.actualizarComentario(publicacionId, comentarioId, comentarioDTO);
		return new ResponseEntity<>(comentarioActualizado,HttpStatus.OK);
	}
	
	
	@PostMapping("/publicaciones/{publicacionId}/comentario")
	public ResponseEntity<ComentarioDTO> guardarComentario(@PathVariable(name = "publicacionId")long publicacionId,
			@Valid @RequestBody ComentarioDTO comentarioDTO){
		return new ResponseEntity<>(comentarioServicio.crearComentario(publicacionId, comentarioDTO),HttpStatus.CREATED);
	}
	
	@DeleteMapping("/publicaciones/{publicacionId}/comentario/{id}")
	public ResponseEntity<String> eliminarComentario(@PathVariable(name = "publicacionId")Long publicacionId,
			@PathVariable(name = "id")Long comentarioId){
		comentarioServicio.eliminarComentario(publicacionId, comentarioId);
		return new ResponseEntity<>("Registro eliminado con exito",HttpStatus.OK);
	}
}
