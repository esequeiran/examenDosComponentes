package com.cenfotec.workshop.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cenfotec.workshop.domain.Categoria;
import com.cenfotec.workshop.repo.CategoriaRepository;

@Service
public class CategoriaServiceImpl implements CategoriaService {

	@Autowired
	CategoriaRepository repo;
	
	@Override
	public void save(Categoria categoria) {
		repo.save(categoria);		
	}

	@Override
	public Optional<Categoria> get(Long id) {		
		return repo.findById(id);
	}

	@Override
	public List<Categoria> getAll() {		
		return repo.findAll();
	}

	/*
	@Override
	public List<Categoria> find(String nombre) {		
		return repo.findByNameContaining(nombre);
	}	
	*/
	@Override
	public void deleteCategoria(Long id) {
		repo.deleteById(id);		
	}

	
}
