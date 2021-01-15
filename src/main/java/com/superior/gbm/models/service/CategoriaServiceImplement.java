package com.superior.gbm.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.superior.gbm.models.entity.Categoria;

import com.superior.gbm.models.repository.CategoriaRepository;


@Service
public class CategoriaServiceImplement implements ICategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	
	@Override
	public List<Categoria> listaCategorias() {
	
		return  (List<Categoria>) categoriaRepository.findAll();
	}

}
