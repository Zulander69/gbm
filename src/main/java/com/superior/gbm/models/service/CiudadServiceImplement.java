package com.superior.gbm.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.superior.gbm.models.entity.Ciudad;
import com.superior.gbm.models.repository.CiudadRepository;

@Service
public class CiudadServiceImplement implements ICiudadService {

	@Autowired
	private CiudadRepository ciudadRepository;
	
	
	@Override
	public List<Ciudad> listaCiudades() {
	
		return  (List<Ciudad>) ciudadRepository.findAll();
	}

}
