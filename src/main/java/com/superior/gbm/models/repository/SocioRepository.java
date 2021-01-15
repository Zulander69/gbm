package com.superior.gbm.models.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.superior.gbm.models.entity.Categoria;
import com.superior.gbm.models.entity.Socio;

@Repository
public interface SocioRepository extends CrudRepository<Socio, Long> {
	//public Socio findBycorreo1(String correo);
	public Set<Socio> findBycorreo(String correo);
	public Set<Socio> findBycelular(String celular);
	public List<Socio> findBycategoria(Categoria categoria);

}
