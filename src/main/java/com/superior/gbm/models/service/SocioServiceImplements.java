package com.superior.gbm.models.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.superior.gbm.models.entity.Categoria;
import com.superior.gbm.models.entity.Socio;
import com.superior.gbm.models.repository.SocioRepository;

@Service
public class SocioServiceImplements implements ISocioService {
	
	@Autowired
	private SocioRepository socioRepository;
	
	@Override
	public List<Socio> listarTodos() {
	
		return (List<Socio>) socioRepository.findAll();
	}

	@Override
	public void guardar(Socio socio) {
		socioRepository.save(socio);

	}

	@Override
	public Socio buscarPorId(Long id) {
		
		return socioRepository.findById(id).orElse(null) ;
	}

	@Override
	public void eliminar(Long id) {
		socioRepository.deleteById(id);

	}

	@Override
	public Set<Socio> buscarPorCorreo(String correo) {
		
		return socioRepository.findBycorreo(correo) ; 
	}
	
	/*
	 * @Override public Socio buscarPorCorreo1(String correo) {
	 * 
	 * return socioRepository.findBycorreo(correo) ; }
	 */
	
	@Override
	public Set<Socio> buscarPorCelular(String celular) {
		
		return socioRepository.findBycelular(celular) ; 
	}
	
	
	@Override
	public List<Socio> buscarPorCategoria(Categoria categoria) {
		
		return socioRepository.findBycategoria(categoria);
	}

}
