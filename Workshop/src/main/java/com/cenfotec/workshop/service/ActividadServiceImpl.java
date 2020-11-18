package com.cenfotec.workshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cenfotec.workshop.domain.Actividad;
import com.cenfotec.workshop.repo.ActividadRepository;



@Service
public class ActividadServiceImpl implements ActividadService {

	@Autowired
	ActividadRepository repo;
	
	@Override
	public void save(Actividad actividad) {
		repo.save(actividad);		
	}

}
