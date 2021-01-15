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
@RequestMapping("/views/productos")
public class ProductoController {
	
	@Autowired
	private ISocioService socioService;
	
	@Autowired
	private IProductoService productoService;
	
	@Autowired
	private ICategoriaService categoriaService;
	
	
	@GetMapping("/")
	public String listarProductos(Model model ) {
		
		List<Producto> listadoProductos= productoService.listarTodos();
	
		
		model.addAttribute("titulo","Lista de Productos");
		model.addAttribute("productos",listadoProductos);
	
		System.out.println(listadoProductos.get(0).getSocio().getNombre());
		return "/views/productos/ListaProductos";
	}
	
	
	@GetMapping("/producto/{id}")
	public String mostrar(@PathVariable("id") Long idProducto, Model modelI) {
		
		Producto producto= productoService.buscarPorId(idProducto);
		
		int viejop = producto.getPrecio();
		int nuevop = (int) (producto.getPrecio()-(producto.getPrecio()*0.2));
		
		modelI.addAttribute("titulo",producto.getNombre());
		modelI.addAttribute("detalle",producto.getDetalle());
		modelI.addAttribute("logo",producto.getSocio().getLogo());
		modelI.addAttribute("empresa",producto.getSocio().getEmpresa());
		modelI.addAttribute("socioid",producto.getSocio().getId());
		modelI.addAttribute("calificacion",producto.getCalificacion());
		modelI.addAttribute("nuevop",nuevop);
		modelI.addAttribute("viejop",viejop);
		modelI.addAttribute("producto", producto);
		
		return "/views/productos/Producto";
	}

	
	
	
	
	///////////////////////////////////////////////////registramos formulariode datos
	@GetMapping("/registrar/{id}")
	public String crear(@PathVariable("id") Long idSocio, Model model) {
		
		Socio socioA= socioService.buscarPorId(idSocio);
		Producto producto = new Producto();
		List<Categoria> listacategorias = categoriaService.listaCategorias();
		
		
		model.addAttribute("titulo","Nuevo Producto");
		model.addAttribute("producto", producto);
		model.addAttribute("categorias", listacategorias);
		model.addAttribute("idSocio", socioA.getId());
		model.addAttribute("logoSocio", socioA.getLogo());
		
		
		
		return "/views/productos/RegistrarProducto";
	}
	
	
	@PostMapping("/save")
	public String guardar(@Valid @ModelAttribute Producto producto, BindingResult result, 
			Model model, RedirectAttributes atributo) {
		
	// voy a buscar erroreen en el formulario si todo va bien sigue
		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"+producto);
		producto.setBaner_producto("0");
		producto.setCalificacion(5);
		producto.setImagen_producto("0");
		
		Socio Socio= socioService.buscarPorId(producto.getSocio().getId());
		
		producto.setSocio(Socio);
		List<Categoria> listacategorias = categoriaService.listaCategorias();
		
		if(result.hasErrors()) {
			model.addAttribute("idSocio", Socio.getId());
			model.addAttribute("categorias", listacategorias);
		    model.addAttribute("titulo","Nuevo Producto");
			model.addAttribute("producto", producto);
			
			System.out.println("Existieron errores en el Formulario");
			return "/views/productos/RegistrarProducto";
		}
		
			
		
		productoService.guardar(producto);
		
		List<Producto> listadoProductos= productoService.listarTodos();
		int ultimoRegistro=(listadoProductos.size()-1);
		System.out.println("/////////////////////////////////////////CLIENTE  GUARDADO CON EXITOhhhhhhhhhhhhh"+listadoProductos.get(ultimoRegistro));
		long idProducto=listadoProductos.get(ultimoRegistro).getId();
		model.addAttribute("idProducto", idProducto);
		
		return "redirect:/views/productos/imagenesproducto/"+idProducto;
		
	}
	
////////////////////////////////////////////////////////registramos imagenes
@GetMapping("/imagenesproducto/{id}")
public String crearI(@PathVariable("id") Long idProducto, Model model) {


Socio socioA= productoService.buscarPorId(idProducto).getSocio();

Producto producto = productoService.buscarPorId(idProducto);

//System.out.println("+++++++++fffffffffffffffffffffffffffff++++++++++++++"+socioA.getApellido());

model.addAttribute("titulo","Nuevo Producto");
model.addAttribute("subt","IMAGENES");

model.addAttribute("producto", producto);

model.addAttribute("idSocio", socioA.getId());

model.addAttribute("logoSocio", socioA.getLogo());


return "/views/productos/ImagenesProducto";
}
/////////////////////////////////////////////////////////////////////////////subir imagenes fisicas
@PostMapping("/uploadMultiple")
public ResponseEntity<?> uploadMultipleFiles(@ModelAttribute Producto producto, @RequestParam("files") List<MultipartFile> files){
    
	Producto ProductoA= productoService.buscarPorId(producto.getId());
	System.out.println("+++++++++fffffffffffffffffffffffffffff++++++++++++++rrrrrr"+ProductoA);
	
	 Long idProducto = ProductoA.getId();
	 String empresaProducto = ProductoA.getSocio().getEmpresa();
	 Long idSocio = ProductoA.getSocio().getId();
	

	//int actualizar = socioA.getActualizar();		//es un valor que actualzia y graba con otro nombre para actuliazar acttomatico			
	int va=0;

	
	
	
	System.out.println("tamaño de files: "+files.size());
	
	if(files.size() == 0){    
        return new ResponseEntity<Object>("Seleccionar algun archivo",HttpStatus.OK);
    }
	
	if(files.size() > 0){    
		
		
		
			
	
		
		
		
		for(int i=0;i<files.size();i++) {
			System.out.println("+++++++++++++++++++++++"+files.get(i).getOriginalFilename()+"AA");
			
			 

			// si el archivo esta enblanco
			if(files.get(i).getOriginalFilename().contentEquals("")) {
			
		        
				System.err.println("mierda no guardo nada chucha");
			
		}
		
		/////////////////////////////comienza la tarea cuadno el archivo existe su nombre no es ta en blanco
		else {
			
			String[] fileFrags = files.get(i).getOriginalFilename().split("\\."); 
				      String extension = fileFrags[fileFrags.length-1];//OBTENGO LA EXTEBCION
			System.out.println("0000000000000000"+extension);
			//////////////////////////////////////////////////////////// solo puede iniciar si exite un control de tipos de datos
			if(extension.contentEquals("jpg") || extension.contentEquals("bmp") ||  extension.contentEquals("png") || extension.contentEquals("gif") )
			{
				
				System.out.println("LA EXTENCION ES:"+extension);
				
			
				
				//////////////////////////////////////////creamos carpeta para cada socio///////////
				
				//File directorio = new File("c:/img/static/files/"+socio.getId()+"_"+socio.getEmpresa());
				File directoriobase = new File("src//main//resources//static//ImagesProduct//"+idSocio);
				
				if (!directoriobase.exists()) {
		            if (directoriobase.mkdirs()) {
		                System.out.println("Directorio creado"+directoriobase);
		            } else {
		                System.out.println("Error al crear directorio");
		            }
		        }
				
				File directorio = new File(directoriobase+"//"+idProducto);
				
				if (!directorio.exists()) {
		            if (directorio.mkdirs()) {
		                System.out.println("Directorio creado"+directorio);
		            } else {
		                System.out.println("Error al crear directorio");
		            }
		        }
				
		
				
			
					
				///if de control que no existe en la base de datos el nombre del archivo		
if(i==0) {// slider 1
	
	
	 System.out.println("Error al crear directorio33333333333333333333333333333333333333333");
	File s1 = new File(directorio+"//s1");
	if (!s1.exists()) {
        if (s1.mkdirs()) {
            System.out.println("Directorio creado"+s1);
        } else {
            System.out.println("Error al crear directorio");
        }
    }
	
	String p1 = s1.toString();
	Path p2 = Paths.get(p1);//creamos un PATH
	
	////////////////////////////////////////////////////////////////////////////
	
    Path TO = Paths.get(s1+"//"+empresaProducto+"I"+i+"."+extension);
	String p = TO.toString();
	
	Path TOb = Paths.get("/files/"+idSocio+"_"+empresaProducto+"/s1/"+empresaProducto+"I"+i+"."+extension);//sin todo el directorio
	String pb = TOb.toString();
	
	System.out.println(p);
	///////////////////////////////////////////////// teniendo los datos coparamos que noexista ese nombre en la base de datos
	
	
    
	
System.out.println("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwww:");
final File[] files1 = s1.listFiles(); for (File f: files1) f.delete();
try {
	Files.copy(files.get(i).getInputStream(), TO);
	producto.setBaner_producto(pb);
	
} catch (Exception e1) {
	try {
		Files.copy(files.get(i).getInputStream(), TO);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	producto.setBaner_producto(pb);
	
}

// lo que hacemos es borrar el directrio antes de grabar un archivo

} 


if(i==1) {// slider 1
	
	
	 System.out.println("Error al crear directorio33333333333333333333333333333333333333333");
	File s2 = new File(directorio+"//s2");
	if (!s2.exists()) {
       if (s2.mkdirs()) {
           System.out.println("Directorio creado"+s2);
       } else {
           System.out.println("Error al crear directorio");
       }
   }
	
	String p1 = s2.toString();
	Path p2 = Paths.get(p1);//creamos un PATH
	
	////////////////////////////////////////////////////////////////////////////
	
   Path TO = Paths.get(s2+"//"+empresaProducto+"I"+i+"."+extension);
	String p = TO.toString();
	
	Path TOb = Paths.get("/files/"+idSocio+"_"+empresaProducto+"/s2/"+empresaProducto+"I"+i+"."+extension);//sin todo el directorio
	String pb = TOb.toString();
	
	System.out.println(p);
	///////////////////////////////////////////////// teniendo los datos coparamos que noexista ese nombre en la base de datos
	
	
   
	
System.out.println("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwww:");
final File[] files1 = s2.listFiles(); for (File f: files1) f.delete();
try {
	Files.copy(files.get(i).getInputStream(), TO);
	producto.setImagen_producto(pb);
	
} catch (Exception e1) {
	try {
		Files.copy(files.get(i).getInputStream(), TO);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	producto.setImagen_producto(pb);
	
}

//lo que hacemos es borrar el directrio antes de grabar un archivo

} 

										

										
				}//if de extencion 
			
			
			
			
			
		//else de extension
			else {
				
							
			
					System.out.println("LA EXTENCION inavlida ES:"+extension);
					String p =".//src//main//resources//files//slider//1-2.jpg";
					//socio.setSlider1(p);
					
				
					
					}
			
			
			productoService.guardar(producto);
			
			
				 
			
			
			/*
			 * // este serviCIo no lo uso por que copio y necesit otros datos del
			 * controlador try {
			 * 
			 * UploadFileService.saveMultipleFiles(files); } catch (IOException e) {
			 * e.printStackTrace(); }
			 */
			
		
		}// ELSE PRINCIPAL
		}// FOR
		
	
	
		
		URI yahoo = null;
		try {
			yahoo = new URI("http://localhost:8090/views/productos/");
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    HttpHeaders httpHeaders = new HttpHeaders();
	    httpHeaders.setLocation(yahoo);
	    return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
		
	//	return new ResponseEntity<Object>("Archivos subidos correctamente",HttpStatus.OK);
        

    
	} // IF PRINCIPAL CUADNO ES MAYOR QUE CERO LENGTH
	return new ResponseEntity<Object>("nosubio",HttpStatus.OK);
    
  
    

}

	
	
}
