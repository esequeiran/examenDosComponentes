package com.cenfotec.workshop.service;

import java.util.List;
import java.util.Optional;

import com.cenfotec.workshop.domain.Workshop;



public interface WorkshopService {

	public void save(Workshop workshop);
	public Optional<Workshop> get(Long id);
	public List<Workshop> getAll();
	public List<Workshop> find(String palabra);
	public List<Workshop> findByCategoria(Long id);
	public List<Workshop> findByPalabrasClave(String palabraClave);
	public List<Workshop> findByAutor(String Autor);
}
