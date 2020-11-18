package com.cenfotec.workshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cenfotec.workshop.domain.Workshop;
import com.cenfotec.workshop.repo.WorkshopRepository;

@Service
public class WorkshopServiceImpl implements WorkshopService {

	@Autowired
	WorkshopRepository repo;
	
	@Override
	public void save(Workshop workshop) {
		repo.save(workshop);
		
	}

	@Override
	public Optional<Workshop> get(Long id) {
		
		return repo.findById(id);
	}

	@Override
	public List<Workshop> getAll() {
		
		
		
		return repo.findAll();
	}

	@Override
	public List<Workshop> find(String palabra) {
		
		return repo.findByNombreContaining(palabra);
	}

	@Override
	public List<Workshop> findByCategoria(Long id) {
		
		
		System.out.println("repo:"+repo.findByCategoriaId(id));
		return repo.findByCategoriaId(id) ;
	}

	@Override
	public List<Workshop> findByPalabrasClave(String palabraClave) {
		
		return repo.findByPalabrasClaveContainingIgnoreCase(palabraClave);
	}

	@Override
	public List<Workshop> findByAutor(String autor) {
		
		return repo.findByAutor(autor);
	}

}
