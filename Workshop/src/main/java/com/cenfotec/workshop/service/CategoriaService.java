package com.cenfotec.workshop.service;

import java.util.List;
import java.util.Optional;

import com.cenfotec.workshop.domain.Categoria;



public interface CategoriaService {

	public void save(Categoria categoria);
	public Optional<Categoria> get(Long id);
	public List<Categoria> getAll();
	/*
	public List<Categoria> find(String nombre);
	*/
	public void deleteCategoria(Long id);
	
}
