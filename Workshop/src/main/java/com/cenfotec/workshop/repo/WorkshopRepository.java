package com.cenfotec.workshop.repo;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cenfotec.workshop.domain.Workshop;


public interface WorkshopRepository extends JpaRepository<Workshop, Long> {
	public List<Workshop> findByNombreContaining(String word);	
	
	public List<Workshop> findByCategoriaId(Long id);	
	
	public List<Workshop> findByPalabrasClaveContainingIgnoreCase(String palabraClave);
	public List<Workshop> findByAutor(String autor);
	
}
