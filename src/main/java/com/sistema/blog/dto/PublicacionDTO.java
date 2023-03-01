package com.sistema.blog.dto;

import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


import com.sistema.blog.entidades.Comentario;

public class PublicacionDTO {

	public String getContenido() {
		return contenido;
	}

	private Long id;

	@NotEmpty 
	@Size(min = 2 )
	private String titulo;

	@NotEmpty 
	@Size(min = 10 )
	private String descripcion;

	@NotEmpty 
	private String contenido;
	
	private Set<Comentario> comentarios;

	public PublicacionDTO() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public Set<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(Set<Comentario> comentarios) {
		this.comentarios = comentarios;
	}
	
	


	
}
