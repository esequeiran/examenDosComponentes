package com.cenfotec.workshop.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.SortComparator;


@Entity
public class Workshop {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;	
	
	private String nombre;
	
	private String autor;
	
	private String objetivo;
	
	private String palabrasClave;
	
	private Double tiempoDuracion;
	
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "categoria_id")
	private Categoria categoria;
	
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="workshop")
	@OrderBy("fechaPublicacion ASC")
	private Set<Actividad> actividades;
	
	@Transient
	private Long idCategoria;

	public Long getIdCategoria() {
		return idCategoria;
	}


	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getAutor() {
		return autor;
	}


	public void setAutor(String autor) {
		this.autor = autor;
	}


	public String getObjetivo() {
		return objetivo;
	}


	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}


	public String getPalabrasClave() {
		return palabrasClave;
	}


	public void setPalabrasClave(String palabrasClave) {
		this.palabrasClave = palabrasClave;
	}


	public Double getTiempoDuracion() {
		return tiempoDuracion;
	}


	public void setTiempoDuracion(Double tiempoDuracion) {
		this.tiempoDuracion = tiempoDuracion;
	}


	public Categoria getCategoria() {
		return categoria;
	}


	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}


	public Set<Actividad> getActividades() {
		return actividades;
	}


	public void setActividades(Set<Actividad> actividades) {
		this.actividades = actividades;
	}




	
}
