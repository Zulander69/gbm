package com.superior.gbm.models.service;

import java.util.List;
import java.util.Set;

import com.superior.gbm.models.entity.Categoria;
import com.superior.gbm.models.entity.Socio;

public interface ISocioService {
	
	public List<Socio> listarTodos();
	public void guardar (Socio cliente);
	public Socio buscarPorId(Long id);
	public Set<Socio> buscarPorCorreo (String correo);
	//public Socio buscarPorCorreo1 (String correo);
	public Set<Socio> buscarPorCelular (String celular);
	public void eliminar (Long id);
	public List<Socio> buscarPorCategoria (Categoria categoria);
	
	
}
