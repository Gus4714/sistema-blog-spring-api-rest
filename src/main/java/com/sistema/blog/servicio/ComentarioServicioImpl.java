package com.sistema.blog.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistema.blog.dto.ComentarioDTO;
import com.sistema.blog.dto.PublicacionDTO;
import com.sistema.blog.entidades.Comentario;
import com.sistema.blog.entidades.Publicacion;
import com.sistema.blog.excepciones.ResourceNotFoundException;
import com.sistema.blog.repositorio.ComentarioRepositorio;
import com.sistema.blog.repositorio.PublicacionRepositorio;

@Service
public class ComentarioServicioImpl implements ComentarioServicio {

	@Autowired
	PublicacionRepositorio publicacionRepositorio;
	
	@Autowired
	ComentarioRepositorio comentarioRepositorio;
	
	@Override
	public ComentarioDTO crearComentario(long publicacionId, ComentarioDTO comentarioDTO) {
		Comentario comentario = mapearEntidad(comentarioDTO);
		 Publicacion publicacion =  publicacionRepositorio.findById(publicacionId)
				 .orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", publicacionId) );
		 
		 comentario.setPublicacion(publicacion);
		 
		 Comentario nuevoComentario = comentarioRepositorio.save(comentario);
		
		return mapearDTO(nuevoComentario);
	}
	
	
	public Comentario mapearEntidad(ComentarioDTO comentarioDTO) {
		Comentario comentario = new Comentario();
		
		comentario.setNombre(comentarioDTO.getNombre());
		comentario.setEmail(comentarioDTO.getEmail());
		comentario.setCuerpo(comentarioDTO.getEmail());
		
		return comentario;	
	}
	
	//Convertimos de entidad a DTO para responder
	public ComentarioDTO mapearDTO(Comentario comentario) {
		ComentarioDTO comentarioDTO = new ComentarioDTO();
		
		comentarioDTO.setId(comentario.getId());
		comentarioDTO.setNombre(comentario.getNombre());
		comentarioDTO.setEmail(comentario.getEmail());
		comentarioDTO.setCuerpo(comentario.getCuerpo());
		
		return comentarioDTO;
	}

}
