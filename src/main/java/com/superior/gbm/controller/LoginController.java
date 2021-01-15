				package com.superior.gbm.controller;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.superior.gbm.models.entity.Categoria;
import com.superior.gbm.models.entity.Ciudad;
import com.superior.gbm.models.entity.Producto;
import com.superior.gbm.models.entity.Socio;
import com.superior.gbm.models.service.ICategoriaService;
import com.superior.gbm.models.service.ICiudadService;
import com.superior.gbm.models.service.IProductoService;
import com.superior.gbm.models.service.ISocioService;
import com.superior.gbm.models.service.UploadFileService;



@Controller
@RequestMapping("/views/socios")
public class LoginController {
	
	@Autowired
	private ISocioService socioService;
	
	@Autowired
	private IProductoService productoService;
	
	@Autowired
	private ICategoriaService categoriaService;
	
	

	@GetMapping("/mostrar/login")
	public String mostrar(Model modelI) {
		
		
		//System.out.println(categoriasocio.get(0));
		//System.out.println("55555555555555555");
		//System.out.println(categoriasocio.get(1));
		
		modelI.addAttribute("titulo","Login");
	

		return "/views/socios/Login";
	}
	
	
	
	
	
}
