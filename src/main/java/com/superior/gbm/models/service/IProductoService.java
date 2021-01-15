package com.superior.gbm.models.service;

import java.util.List;
import java.util.Set;

import com.superior.gbm.models.entity.Producto;


public interface IProductoService {
	
	public List<Producto> listarTodos();
	public void guardar (Producto producto);
	public Producto buscarPorId(Long id);
	public Set<Producto> buscarPorNombre (String nombre);
	//public Set<Socio> buscarPorCelular (String celular);
	public void eliminar (Long id);
	
}
