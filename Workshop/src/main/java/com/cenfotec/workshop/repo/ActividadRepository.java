package com.cenfotec.workshop.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cenfotec.workshop.domain.Actividad;


public interface ActividadRepository extends JpaRepository<Actividad, Long> {
	
}
