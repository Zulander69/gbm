package com.superior.gbm.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.superior.gbm.models.entity.Categoria;
import com.superior.gbm.models.entity.Producto;
import com.superior.gbm.models.entity.Socio;
import com.superior.gbm.models.service.ICategoriaService;
import com.superior.gbm.models.service.IProductoService;
import com.superior.gbm.models.service.ISocioService;

@Controller
@RequestMapping("/")
public class HomeController {
	@Autowired
	private ISocioService socioService;
	@Autowired
	private IProductoService productoService;
	@Autowired
	private ICategoriaService categoriaService;
	
	@GetMapping("/")
	public String home(Model model) {
		List<Producto> listadoProductos= productoService.listarTodos();
		List<Socio> listadoSocios= socioService.listarTodos();
		List<Categoria> listadoCategorias = categoriaService.listaCategorias();
		
		List<Socio> so = new ArrayList<Socio>();
		
		
		so.add(0, listadoSocios.get(0));
		so.add(1, listadoSocios.get(1));
		so.add(2, listadoSocios.get(2));
		so.add(3, listadoSocios.get(3));
		
		List<Socio> so1=so;
		
List<Producto> pro = new ArrayList<Producto>();
		
		pro.add(0, listadoProductos.get(0));
		pro.add(1, listadoProductos.get(1));
		pro.add(2, listadoProductos.get(2));
		pro.add(3, listadoProductos.get(3));
		
		List<Producto> pro1=pro;
		
		System.out.println("66666666666666666666666666666666666666666"+so);
		
		model.addAttribute("titulo","SOCIO DESTACADOS");
		model.addAttribute("titulo2","NUEVOS PRODUCTOS");
		
		model.addAttribute("so1",so1);
		model.addAttribute("pro1",pro1);
		model.addAttribute("cat1",listadoCategorias);
		return "/Home";
	}
	
	
	
}


