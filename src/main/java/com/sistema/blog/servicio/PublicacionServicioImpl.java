package com.sistema.blog.servicio;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sistema.blog.dto.PublicacionDTO;
import com.sistema.blog.dto.PublicacionRespuesta;
import com.sistema.blog.entidades.Publicacion;
import com.sistema.blog.excepciones.ResourceNotFoundException;
import com.sistema.blog.repositorio.PublicacionRepositorio;

@Service
public class PublicacionServicioImpl implements PublicacionServicio{

	@Autowired
	ModelMapper modelMapper;
	
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
	public PublicacionRespuesta obtenerTodasLasPublicaciones(int numeroDePagina, int medidaDePagina,String ordenarPor, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(ordenarPor).ascending():Sort.by(ordenarPor).descending();
		//SE APLICAN LOS PARAMETROS
		Pageable pageable = PageRequest.of(numeroDePagina, medidaDePagina,sort);
		//SE CARGAN LOS DATOS OBTENIDOS DE BD
		Page<Publicacion> publicaciones = publicacionRepositorio.findAll(pageable);
		//SE CONVIERTE A TIPO PUBLICACION
		List<Publicacion> listaDePublicaciones = publicaciones.getContent();
		//SE MAPEA A DTO
		List<PublicacionDTO> contenido =  listaDePublicaciones.stream().map(publicacion -> mapearDTO(publicacion)).collect(Collectors.toList());
		
		PublicacionRespuesta publicacionRespuesta = new PublicacionRespuesta();
		publicacionRespuesta.setContenido(contenido);
		publicacionRespuesta.setNumeroPagina(publicaciones.getNumber());
		publicacionRespuesta.setMedidaPagina(publicaciones.getSize());
		publicacionRespuesta.setTotalElementos(publicaciones.getTotalElements());
		publicacionRespuesta.setTotalPaginas(publicaciones.getTotalPages());
		publicacionRespuesta.setUltima(publicaciones.isLast());
		
		return publicacionRespuesta;
		
	}
	
	// Convertimos de DTO a entidad para persistir
	public Publicacion mapearEntidad(PublicacionDTO publicacionDTO) {
		Publicacion publicacion = modelMapper.map(publicacionDTO, Publicacion.class);	
		return publicacion;	
	}
	
	//Convertimos de entidad a DTO para responder
	public PublicacionDTO mapearDTO(Publicacion publicacion) {
		PublicacionDTO publicacionRespuesta = modelMapper.map(publicacion, PublicacionDTO.class);	
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
