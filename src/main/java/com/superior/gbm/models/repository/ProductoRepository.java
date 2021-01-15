package com.superior.gbm.models.repository;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.superior.gbm.models.entity.Producto;


@Repository
public interface ProductoRepository extends CrudRepository<Producto, Long> {

	public Set<Producto> findBynombre(String nombre);
	//public Set<Producto> findBycelular(String celular);

}
