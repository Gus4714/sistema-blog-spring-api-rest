package com.sistema.blog.servicio;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sistema.blog.dto.ComentarioDTO;
import com.sistema.blog.entidades.Comentario;
import com.sistema.blog.entidades.Publicacion;
import com.sistema.blog.excepciones.BlogAppException;
import com.sistema.blog.excepciones.ResourceNotFoundException;
import com.sistema.blog.repositorio.ComentarioRepositorio;
import com.sistema.blog.repositorio.PublicacionRepositorio;

@Service
public class ComentarioServicioImpl implements ComentarioServicio {

	@Autowired
	ModelMapper modelMapper;
	
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
		Comentario comentario = modelMapper.map(comentarioDTO, Comentario.class);	
		return comentario;	
	}
	
	//Convertimos de entidad a DTO para responder
	public ComentarioDTO mapearDTO(Comentario comentario) {
		ComentarioDTO comentarioDTO = modelMapper.map(comentario, ComentarioDTO.class);		
		return comentarioDTO;
	}


	@Override
	public List<ComentarioDTO> obtenerComentarioPorPublicacionId(long publicacionId) {
		List<Comentario> comentarios = comentarioRepositorio.findByPublicacionId(publicacionId);
		return comentarios.stream().map(comentario -> mapearDTO(comentario)).collect(Collectors.toList());
	}


	@Override
	public ComentarioDTO obtenerComentarioPorId(long publicacionId, long comentarioId) {
		 Publicacion publicacion =  publicacionRepositorio.findById(publicacionId)
				 .orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", publicacionId) );
		 
		 Comentario comentario =  comentarioRepositorio.findById(comentarioId)
				 .orElseThrow(() -> new ResourceNotFoundException("Comentario", "id", comentarioId) );
		
		
		 
		 if(!comentario.getPublicacion().getId().equals(publicacion.getId())) {
			 throw new BlogAppException(HttpStatus.BAD_REQUEST, "El comentario no pertece a la publicacion");
		 }
		 
		return mapearDTO(comentario);
	}


	@Override
	public ComentarioDTO actualizarComentario(long publicacionId, long comentarioId, ComentarioDTO comentarioDTO) {
		Publicacion publicacion =  publicacionRepositorio.findById(publicacionId)
				 .orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", publicacionId) );
		 
		 Comentario comentario =  comentarioRepositorio.findById(comentarioId)
				 .orElseThrow(() -> new ResourceNotFoundException("Comentario", "id", comentarioId) );
		
		
		 
		 if(!comentario.getPublicacion().getId().equals(publicacion.getId())) {
			 throw new BlogAppException(HttpStatus.BAD_REQUEST, "El comentario no pertece a la publicacion");
		 }
		 
		 comentario.setCuerpo(comentarioDTO.getCuerpo());
		 comentario.setEmail(comentarioDTO.getEmail());
		 comentario.setNombre(comentarioDTO.getNombre());
		 
		 Comentario comentarioActualizado = comentarioRepositorio.save(comentario);
		 
		 return mapearDTO(comentarioActualizado);
	}


	@Override
	public void eliminarComentario(long publicacionId, long comentarioId) {
		Publicacion publicacion =  publicacionRepositorio.findById(publicacionId)
				 .orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", publicacionId) );
		 
		 Comentario comentario =  comentarioRepositorio.findById(comentarioId)
				 .orElseThrow(() -> new ResourceNotFoundException("Comentario", "id", comentarioId) );
		
		
		 
		 if(!comentario.getPublicacion().getId().equals(publicacion.getId())) {
			 throw new BlogAppException(HttpStatus.BAD_REQUEST, "El comentario no pertece a la publicacion");
		 }
		 
		 comentarioRepositorio.delete(comentario);
	}

}
