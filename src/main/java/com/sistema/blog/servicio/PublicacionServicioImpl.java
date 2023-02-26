package com.sistema.blog.servicio;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sistema.blog.dto.PublicacionDTO;
import com.sistema.blog.entidades.Publicacion;
import com.sistema.blog.excepciones.ResourceNotFoundException;
import com.sistema.blog.repositorio.PublicacionRepositorio;

@Service
public class PublicacionServicioImpl implements PublicacionServicio{

	@Autowired
	PublicacionRepositorio publicacionRepositorio;
	
	@Override
	public PublicacionDTO crearPublicacion(PublicacionDTO publicacionDTO) {
		// Convertimos de DTO a entidad para persistir
		Publicacion publicacion =  mapearEntidad(publicacionDTO);
		
		Publicacion nuevaPublicacion = publicacionRepositorio.save(publicacion);
		
		//Convertimos de entidad a DTO para responder
		PublicacionDTO publicacionRespuesta = mapearDTO(nuevaPublicacion);
		
		return publicacionRespuesta;
	}

	@Override
	public List<PublicacionDTO> obtenerTodasLasPublicaciones(int numeroDePagina, int medidaDePagina) {
		Pageable pageable = PageRequest.of(numeroDePagina, medidaDePagina);
		Page<Publicacion> publicaciones = publicacionRepositorio.findAll(pageable);
		
		List<Publicacion> listaDePublicaciones = publicaciones.getContent();
		return listaDePublicaciones.stream().map(publicacion -> mapearDTO(publicacion)).collect(Collectors.toList());
	}
	
	// Convertimos de DTO a entidad para persistir
	public Publicacion mapearEntidad(PublicacionDTO publicacionDTO) {
		Publicacion publicacion = new Publicacion();
		
		publicacion.setTitulo(publicacionDTO.getTitulo());
		publicacion.setContenido(publicacionDTO.getContenido());
		publicacion.setDescripcion(publicacionDTO.getDescripcion());
		
		return publicacion;	
	}
	
	//Convertimos de entidad a DTO para responder
	public PublicacionDTO mapearDTO(Publicacion publicacion) {
		PublicacionDTO publicacionRespuesta = new PublicacionDTO();
		
		publicacionRespuesta.setId(publicacion.getId());
		publicacionRespuesta.setTitulo(publicacion.getTitulo());
		publicacionRespuesta.setContenido(publicacion.getContenido());
		publicacionRespuesta.setDescripcion(publicacion.getDescripcion());
		
		return publicacionRespuesta;
	}

	@Override
	public PublicacionDTO obtenerPublicacionPorId(long id) {
		 Publicacion publicacion =  publicacionRepositorio.findById(id)
				 .orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", id) );
		return mapearDTO(publicacion);
	}

	@Override
	public PublicacionDTO actualizarPublicacion(PublicacionDTO publicacionDTO, long id) {
		 Publicacion publicacion =  publicacionRepositorio.findById(id)
				 .orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", id) );
		 
		 publicacion.setTitulo(publicacionDTO.getTitulo());
		 publicacion.setContenido(publicacionDTO.getContenido());
		 publicacion.setDescripcion(publicacionDTO.getDescripcion());
		 
	     Publicacion publicacionActualizada = publicacionRepositorio.save(publicacion);
		 
		 return mapearDTO(publicacionActualizada);
	}

	@Override
	public void eliminarPublicacion(long id) {
		 Publicacion publicacion =  publicacionRepositorio.findById(id)
				 .orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", id) );
		publicacionRepositorio.delete(publicacion);
		
	}

}
