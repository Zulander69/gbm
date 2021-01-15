package com.superior.gbm.models.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.superior.gbm.models.entity.Producto;

import com.superior.gbm.models.repository.ProductoRepository;


@Service
public class ProductoServiceImplements implements IProductoService {
	
	@Autowired
	private ProductoRepository productoRepository;
	
	@Override
	public List<Producto> listarTodos() {
	
		return (List<Producto>) productoRepository.findAll();
	}

	@Override
	public void guardar(Producto producto) {
		productoRepository.save(producto);

	}

	@Override
	public Producto buscarPorId(Long id) {
		
		return productoRepository.findById(id).orElse(null) ;
	}

	@Override
	public void eliminar(Long id) {
		productoRepository.deleteById(id);

	}

	@Override
	public Set<Producto> buscarPorNombre(String nombre) {
		
		return productoRepository.findBynombre(nombre) ; 
	}
	


}
