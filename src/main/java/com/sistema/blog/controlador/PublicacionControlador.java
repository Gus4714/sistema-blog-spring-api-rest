package com.sistema.blog.controlador;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.blog.dto.PublicacionDTO;
import com.sistema.blog.dto.PublicacionRespuesta;
import com.sistema.blog.servicio.PublicacionServicio;
import com.sistema.blog.utilerias.AppConstantes;

@RestController
@RequestMapping("/api/publicaciones")
public class PublicacionControlador {

	
	@Autowired
	private PublicacionServicio publicacionServicio;
	
	@PreAuthorize("hasRole('ADMIN1')")
	@GetMapping
	public PublicacionRespuesta listarPublicaciones(
			@RequestParam(value = "pageNo",defaultValue = AppConstantes.NUMERO_DE_PAGINA_POR_DEFECTO,required = false) int numeroPagina,
			@RequestParam(value = "pageSize",defaultValue = AppConstantes.MEDIDA_PAGINA_POR_DEFECTO,required = false) int medidaDePagina,
			@RequestParam(value = "sortBy",defaultValue = AppConstantes.ORDENAR_POR_DEFECTO,required = false) String ordenarPor,
			@RequestParam(value = "sortDir",defaultValue = AppConstantes.ORDENAR_DIRECCION_POR_DEFECTO,required = false) String sortDir){
		return publicacionServicio.obtenerTodasLasPublicaciones(numeroPagina,medidaDePagina,ordenarPor,sortDir);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PublicacionDTO> obtenerPublicacionPorId(@PathVariable("id")long id){
		return ResponseEntity.ok(publicacionServicio.obtenerPublicacionPorId(id));
	}
	
	@PreAuthorize("hasRole('USER')")
	@PostMapping
	public ResponseEntity<PublicacionDTO> guardarPublicacion(@Valid @RequestBody PublicacionDTO publicacionDTO){
		return new ResponseEntity<>(publicacionServicio.crearPublicacion(publicacionDTO),HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasRole('ADMIN1')")
	@PutMapping("/{id}")
	public ResponseEntity<PublicacionDTO> actualizarPublicacion(@Valid @RequestBody PublicacionDTO publicacionDTO,@PathVariable("id")long id){
		PublicacionDTO publicacionRespuesta = publicacionServicio.actualizarPublicacion(publicacionDTO, id);
		return new ResponseEntity<>(publicacionRespuesta,HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN1')")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> eliminarPublicacion(@PathVariable("id")long id){
		publicacionServicio.eliminarPublicacion(id);
		return new  ResponseEntity<>("Publicacion eliminada con exito",HttpStatus.OK);
	}
	
}
