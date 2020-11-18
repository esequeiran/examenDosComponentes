package com.cenfotec.workshop.domain;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;



@Entity
public class Actividad {


	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
	
	private String nombre;
	private String descripcion;
	private String texto;
	private Double tiempoDuracion;
	
	
	@ManyToOne
    @JoinColumn(name="fk_workshop", nullable=false)
	private Workshop workshop;

	
	@DateTimeFormat(pattern="dd-MM-yyyy'T'HH:mm:ss")
	private LocalDate fechaPublicacion;


	
	public Workshop getWorkshop() {
		return workshop;
	}


	public void setWorkshop(Workshop workshop) {
		this.workshop = workshop;
	}


	public LocalDate getFechaPublicacion() {
		return fechaPublicacion;
	}


	public void setFechaPublicacion(LocalDate fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
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


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public String getTexto() {
		return texto;
	}


	public void setTexto(String texto) {
		this.texto = texto;
	}


	public Double getTiempoDuracion() {
		return tiempoDuracion;
	}


	public void setTiempoDuracion(Double tiempoDuracion) {
		this.tiempoDuracion = tiempoDuracion;
	}

/*
	public Workshop getWorkshop() {
		return workshop;
	}


	public void setWorkshop(Workshop workshop) {
		this.workshop = workshop;
	}
*/
	
	
}
