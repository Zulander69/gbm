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
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
import com.superior.gbm.models.entity.Socio;
import com.superior.gbm.models.service.ICategoriaService;
import com.superior.gbm.models.service.ICiudadService;
import com.superior.gbm.models.service.ISocioService;
import com.superior.gbm.models.service.UploadFileService;



@Controller
@RequestMapping("/views/socios")
public class SocioController {
    @Autowired
    private JavaMailSender mailSender;
	
	@Autowired
	private ISocioService socioService;
	
	@Autowired
	private ICiudadService ciudadService;
	
	@Autowired
	private ICategoriaService categoriaService;
	
	
	@GetMapping("/")
	public String listarSocios(Model model) {
		
		List<Socio> listadoSocios= socioService.listarTodos();
		model.addAttribute("titulo","Lista de Socios");
		model.addAttribute("socios",listadoSocios);
		return "/views/socios/ListaSocios";
	}
	
	@GetMapping("/editA")
	public String listarSociosA(Model model) {
		List<Ciudad> listaciudades = ciudadService.listaCiudades();
		List<Socio> listadoSocios= socioService.listarTodos();
		model.addAttribute("titulo","Lista de Socios");
		model.addAttribute("socios",listadoSocios);
		model.addAttribute("ciudades",listaciudades);
		return "/views/socios/EditA";
	}
	

	
	
	@GetMapping("/registrar")
	public String crear(Model model) {
		
		Socio socio = new Socio();
		List<Ciudad> listaciudades = ciudadService.listaCiudades();
		List<Categoria>listacategorias = categoriaService.listaCategorias();
		socio.setSlider1("/images/slider/1-2.jpg");
		socio.setSlider2("/images/slider/1-1.jpg");
		socio.setLogo("/images/logo/L1.png");
		socio.setBaner_empresa("/images/banner/1.jpg");
		socio.setBaner_slogan("/images/banner/1.jpg");
		
		
		System.out.println(socio.getBaner_empresa());
		model.addAttribute("titulo","Nuevo Socio");
		model.addAttribute("socio", socio);
		model.addAttribute("ciudades", listaciudades);
		model.addAttribute("categorias", listacategorias);
		System.out.println(listacategorias);
		System.out.println(listaciudades);
		
		
		return "/views/socios/Registrar";
	}
	
	@PostMapping("/save")
	public String guardar(@Valid @ModelAttribute Socio socio, BindingResult result, 
			Model model, RedirectAttributes atributo) {

	// voy a buscar erroreen en el formulario si todo va bien sigue
		List<Ciudad> listaciudades = ciudadService.listaCiudades();
		List<Categoria>listacategorias = categoriaService.listaCategorias();
		socio.setSlider1("/images/slider/1-2.jpg");
		socio.setSlider2("/images/slider/1-1.jpg");
		socio.setLogo("/images/logo/L1.png");
		socio.setBaner_empresa("/images/banner/1.jpg");
		socio.setBaner_slogan("/images/banner/1.jpg");
		if(result.hasErrors()) {
			
		    model.addAttribute("titulo","Nuevo Socio");
			model.addAttribute("socio", socio);
			model.addAttribute("ciudades", listaciudades);
			model.addAttribute("categorias", listacategorias);
			System.out.println("Existieron errores en el Formulario");
			return "/views/socios/Registrar";
		}
		
		
		if(socio.getCuenta()==(null)) {
			socio.setCuenta(0);
		}
		
		System.out.println("**********************************datos del SOCIO:"+socio);
		Set<Socio> correosocio= socioService.buscarPorCorreo(socio.getCorreo());
		System.out.println(correosocio.size());
		
		
		Set<Socio> celsocio= socioService.buscarPorCelular(socio.getCelular());
		System.out.println(celsocio.size());
		
		if((celsocio.size()==0)&&(correosocio.size()==0)&&(socio.getCuenta()==1)) {
			
					
		
		
		System.out.println("///////////////////////////////////////////////////////////////");
		String P=(socio.getPassword());
		String C=(socio.getConfirm());
		
		if (P.equals(C)) {
			//generamos codigo para activacion
			
				int codigo = (int) (10000 * Math.random());
				socio.setCodigo(codigo);
				//System.out.println("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");
				//System.out.println(socio.getCategoria());
		socioService.guardar(socio);//////////////GUARDAMOS EL SOCIO
		
		System.out.println("/////////////////////////////////////////CLIENTE  GUARDADO CON EXITO");
		
		//////////////////////////////////////////////////////////////////enviando mail////////
		
		// buscamos en la base de datos por correo y enviamo su informacion y tnemos el ID del usuario grabado
		
		Set<Socio> SocioNuevoBase=  socioService.buscarPorCorreo(socio.getCorreo());
		
		Long idS = SocioNuevoBase.iterator().next().getId();
		String nombreS = SocioNuevoBase.iterator().next().getNombre();
		String apellidoS = SocioNuevoBase.iterator().next().getApellido();
		String S = SocioNuevoBase.iterator().next().getApellido();
		String correoS = SocioNuevoBase.iterator().next().getCorreo();
		int codigoS=  SocioNuevoBase.iterator().next().getCodigo();
		
		 
		System.out.println("/////////////////////////////////////////SOCIONUEVO:"+ SocioNuevoBase);
		
	
				 
		 ////////////////////////////////// enviamos mail
		 
		SimpleMailMessage email = new SimpleMailMessage();
		System.out.println("/////////////////////////////////////////CODIGO ES:"+ codigoS);
	        email.setTo(correoS);
	        email.setSubject("REGISTRO_EN_VALLEMART");
	        email.setText("Enhorabuena "+nombreS+"_"+apellidoS+", te has registrado con éxito en la aplicación VALLEMART\n"+
	                "Ahora ERES SOCIO DE LA MEJOR RED DE VENTAS ONLINE.\n"+
	                "-------------------------------------------\n"+
	                "   Tu código PIN para acceder es: "+codigoS+"\n"+
	                "-------------------------------------------\n"+
	                "Esperamos que disfrutes mucho de VALLEMART!");
	        
	        mailSender.send(email);
        
       
	    	System.out.println("/////////////////////////////////////////enviar mail");
		///////////////////////////////////////////////////////////////////////////////////////////////////
		
		
		
	    	System.out.println("/////////////////////////////////////////cuale s el ids:"+idS);
		return "redirect:/views/socios/confirmacion/"+idS;
		
		
		}
		System.out.println("////////////////////////////////no se guardo error de claves putas ");
		System.out.println(P);
		System.out.println(C);
		return "redirect:/views/socios/registrar";
	}
		else {
		System.out.println("/////////////////////////////////////////CORREO O CELULAR EXISTENTES");
		
		return "redirect:/views/socios/registrar";}
	}
	
	
	////////////////////////////////////////////CODIGO REGITRAR CONFIRMACION///////////////////////////////////
	
	@GetMapping("/confirmacion/{id}")
	public String confirmar(@PathVariable("id") Long idSocio, Model model) {
		
			
		Socio socio= socioService.buscarPorId(idSocio);
		model.addAttribute("titulo","CONFIRMACION");
		model.addAttribute("idSocio",idSocio);
		model.addAttribute("socio",socio);
		
		return "/views/socios/confirmacion";
	}
	
	
	@PostMapping("/confirmar")// leemos el codigo de ocnformacion para activar la cuenta
	
	public String activar(@Valid @ModelAttribute Socio socio, BindingResult result, 
			Model model, RedirectAttributes atributo) {
		
		//////////////////////////////////////////////////////////////////
		Socio socioA= socioService.buscarPorId(socio.getId());
		
		socio.setNombre(socioA.getNombre());
		socio.setApellido(socioA.getApellido());
		socio.setDireccion(socioA.getDireccion());
		socio.setCorreo(socioA.getCorreo());
		socio.setPassword(socioA.getPassword());
		socio.setCelular(socioA.getCelular());
		socio.setCuenta(socioA.getCuenta()); ///images/slider/1-2.jpg
		socio.setBaner_empresa("/images/slider/1-2.jpg");
		socio.setSlider1("/images/slider/1-2.jpg");
		socio.setSlider2("/images/slider/1-2.jpg");
		socio.setLogo("/images/slider/1-2.jpg");
		socio.setBaner_slogan("/images/slider/1-2.jpg");
		socio.setEmpresa(socioA.getEmpresa());
		socio.setDetalle(socioA.getDetalle());
		socio.setCiudad(socioA.getCiudad());
		
		/////////////////////////////////////////////////////////////////////
		
		System.out.println("/////////////////////////////////////////---------------"+socio);
		
		
		if(socioA.getCodigo()== socio.getCodigo()) {
			System.out.println("//////////////CODIGOS IGUALES//////////////////////////---------------"+socio);
			socio.setEstado(1);
			socioService.guardar(socio);//////////////GUARDAMOS EL SOCIO
			return "redirect:/views/socios/socio/"+socioA.getId();
		}
		
		return "redirect:/views/socios/confirmacion/"+socioA.getId();
	}
	///////////////////////////////////////////////////////////////////////////////////
	
	
	@GetMapping("/editar/{id}")
	public String crear(@PathVariable("id") Long idSocio, Model model) {
		
		Socio socio= socioService.buscarPorId(idSocio);
		List<Ciudad> listaciudades = ciudadService.listaCiudades();
		
		System.out.println(socio.getBaner_empresa());
		model.addAttribute("titulo","Editar Socio");
		model.addAttribute("subt",socio.getEmpresa());
		model.addAttribute("socio", socio);
		model.addAttribute("ciudades", listaciudades);
		return "/views/socios/Editar";
	}
	///////////////////////////////////MOSTRAMOS LA INROMACION EN LA PAGINA DE SOCIO INDIVIDUAL//////////////
	
	@GetMapping("/socio/{id}")
	public String mostrar(@PathVariable("id") Long idSocioI, Model modelI) {
		
		Socio socio= socioService.buscarPorId(idSocioI);
		List<Ciudad> listaciudades = ciudadService.listaCiudades();
		
		System.out.println(socio.getBaner_empresa());
		modelI.addAttribute("titulo",socio.getEmpresa());
		modelI.addAttribute("subt",socio.getEmpresa());
		modelI.addAttribute("socio", socio);
		modelI.addAttribute("ciudades", listaciudades);
		return "/views/socios/Socio";
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////
	
	///////////////////////////////////MOSTRAMOS LA INROMACION EN LA PAGINA DE SOCIO INDIVIDUAL//////////////
	
	@GetMapping("/perfil/{id}")
	public String perfil(@PathVariable("id") Long idSocioI, Model modelI) {
		
		Socio socio= socioService.buscarPorId(idSocioI);
		List<Ciudad> listaciudades = ciudadService.listaCiudades();
		
		System.out.println(socio.getBaner_empresa());
		modelI.addAttribute("titulo",socio.getEmpresa());
		modelI.addAttribute("subt",socio.getEmpresa());
		modelI.addAttribute("socio", socio);
		modelI.addAttribute("ciudades", listaciudades);
		return "/views/socios/SocioPerfil";
	}
	
	///////////////////////////////////MOSTRAMOS LA INROMACION PRODUCTOS EN LA PAGINA DE SOCIO INDIVIDUAL//////////////
	
	@GetMapping("/perfilproductos/{id}")
	public String perfilproductos(@PathVariable("id") Long idSocioI, Model modelI) {
		
		Socio socio= socioService.buscarPorId(idSocioI);
		List<Ciudad> listaciudades = ciudadService.listaCiudades();
		
		System.out.println(socio.getBaner_empresa());
		modelI.addAttribute("titulo",socio.getEmpresa());
		modelI.addAttribute("subt",socio.getEmpresa());
		modelI.addAttribute("socio", socio);
		modelI.addAttribute("ciudades", listaciudades);
		return "/views/productos/SocioPerfilProductos";
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	@PostMapping("/edit")
	public String editar(@ModelAttribute Socio socio) {
		
		socio.setBaner_empresa("7.jpg");
		
		System.out.println(socio.getCelular());
				
		System.out.println("///////////////////////////////////////////////////////////////");
		String P=(socio.getPassword());
		String C=(socio.getConfirm());
		
		if (P.equals(C)) {
		socioService.guardar(socio);
		
		System.out.println("/////////////////////////////////////////CLIENTE  GUARDADO CON EXITO");
		
		return "redirect:/views/socios/";
		}
		System.out.println("////////////////////////////////no se guardo error de claves putas ");
		System.out.println(P);
		System.out.println(C);
		return "redirect:/views/socios/registrar";
	}
	
	
	//////////////////////////////////////////upload archivo//////////////////////////
	@PostMapping("/upload")
	
    public ResponseEntity<?> uploadFile(@RequestParam("slider1") MultipartFile file1) {
		
		System.out.println("///////////////////////////////post ");
        if (file1.isEmpty()) {
            return new ResponseEntity<Object>("Seleccionar un archivo", HttpStatus.OK);
        }

        try {
            UploadFileService.saveFile(file1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<Object>("Archivo subido correctamente", HttpStatus.OK);
    }
	
	
	/////////////////////////////////////////////// variosarcivos//////////////////////////////
	
	@PostMapping("/uploadMultiple")
    public ResponseEntity<?> uploadMultipleFiles(@ModelAttribute Socio socio, @RequestParam("files") List<MultipartFile> files){
        
		Socio socioA= socioService.buscarPorId(socio.getId());
		socio.setApellido(socioA.getApellido());
		socio.setNombre(socioA.getNombre());
		socio.setApellido(socioA.getApellido());
		socio.setDireccion(socioA.getDireccion());
		socio.setCorreo(socioA.getCorreo());
		socio.setPassword(socioA.getPassword());
		socio.setCelular(socioA.getCelular());
		socio.setCuenta(socioA.getCuenta());
		socio.setEstado(socioA.getEstado());
		socio.setEmpresa(socioA.getEmpresa());
		socio.setDetalle(socioA.getDetalle());
		socio.setCiudad(socioA.getCiudad());
		int actualizar = socioA.getActualizar();		//es un valor que actualzia y graba con otro nombre para actuliazar acttomatico			
		int va=0;

		
		System.out.println("+++++++++fffffffffffffffffffffffffffff++++++++++++++"+actualizar);
		
		System.out.println("tamaño de files: "+files.size());
		
		if(files.size() == 0){    
            return new ResponseEntity<Object>("Seleccionar algun archivo",HttpStatus.OK);
        }
		
		if(files.size() > 0){    
			
			
			
			if (actualizar==0 && va==0){
				actualizar=1;
				socio.setActualizar(actualizar);
				//System.out.println("+++++++++ggggggggggggggggggg++++++++++++++"+actualizar);
				va=1;
			}
			
			if (actualizar==1 && va==0){
				actualizar=0;
				socio.setActualizar(actualizar);

				va=0;
			}
			
		
			
			
			
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
					File directorio = new File("src//main//resources//static//files//"+socio.getId()+"_"+socio.getEmpresa());
					
					if (!directorio.exists()) {
			            if (directorio.mkdirs()) {
			                System.out.println("Directorio creado"+directorio);
			            } else {
			                System.out.println("Error al crear directorio");
			            }
			        }
					
			
					
				
						
				///if de control que no existe en la base de datos el nombre del archivo		
if(i==0) {// slider 1
	
	
	 System.out.println("Error al crear directorio33333333333333333333333333333333333333333"+actualizar);
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
	
    Path TO = Paths.get(s1+"//"+socio.getEmpresa()+"I"+i+actualizar+"."+extension);
	String p = TO.toString();
	
	Path TOb = Paths.get("/files/"+socio.getId()+"_"+socio.getEmpresa()+"/s1/"+socio.getEmpresa()+"I"+i+actualizar+"."+extension);//sin todo el directorio
	String pb = TOb.toString();
	
	System.out.println(p);
	///////////////////////////////////////////////// teniendo los datos coparamos que noexista ese nombre en la base de datos
	
	
    
	
System.out.println("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwww:");
final File[] files1 = s1.listFiles(); for (File f: files1) f.delete();
try {
	Files.copy(files.get(i).getInputStream(), TO);
	socio.setSlider1(pb);
	
} catch (Exception e1) {
	try {
		Files.copy(files.get(i).getInputStream(), TO);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	socio.setSlider1(pb);
	
}

// lo que hacemos es borrar el directrio antes de grabar un archivo

} 
/////////////////////////////////////////////fin de bloque si se borra el archivo grabamos solo si no existe
//////////////////////////si existe lo boirramos y grabamos

///if de control que no existe en la base de datos el nombre del archivo		
if(i==1) {// slider 2

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

Path TO = Paths.get(s2+"//"+socio.getEmpresa()+"I"+i+actualizar+"."+extension);

String p = TO.toString();

Path TOb = Paths.get("/files/"+socio.getId()+"_"+socio.getEmpresa()+"/s2/"+socio.getEmpresa()+"I"+i+actualizar+"."+extension);//sin todo el directorio
String pb = TOb.toString();

System.out.println(p);
///////////////////////////////////////////////// teniendo los datos coparamos que noexista ese nombre en la base de datos




System.out.println("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwww:");
final File[] files1 = s2.listFiles(); for (File f: files1) f.delete();
try {
Files.copy(files.get(i).getInputStream(), TO);
socio.setSlider2(pb);
} catch (Exception e1) {
try {
Files.copy(files.get(i).getInputStream(), TO);
} catch (IOException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}
socio.setSlider2(pb);
}

//lo que hacemos es borrar el directrio antes de grabar un archivo

} 
/////////////////////////////////////////////fin de bloque si se borra el archivo grabamos solo si no existe
//////////////////////////si existe lo boirramos y grabamos


///if de control que no existe en la base de datos el nombre del archivo		
if(i==2) {// Logo

File s3 = new File(directorio+"//s3");
if (!s3.exists()) {
if (s3.mkdirs()) {
System.out.println("Directorio creado"+s3);
} else {
System.out.println("Error al crear directorio");
}
}

String p1 = s3.toString();
Path p2 = Paths.get(p1);//creamos un PATH

////////////////////////////////////////////////////////////////////////////

Path TO = Paths.get(s3+"//"+socio.getEmpresa()+"I"+i+actualizar+"."+extension);

String p = TO.toString();

Path TOb = Paths.get("/files/"+socio.getId()+"_"+socio.getEmpresa()+"/s3/"+socio.getEmpresa()+"I"+i+actualizar+"."+extension);//sin todo el directorio
String pb = TOb.toString();

System.out.println(p);
///////////////////////////////////////////////// teniendo los datos coparamos que noexista ese nombre en la base de datos




System.out.println("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwww:");
final File[] files1 = s3.listFiles(); for (File f: files1) f.delete();
try {
Files.copy(files.get(i).getInputStream(), TO);
socio.setLogo(pb);
} catch (Exception e1) {
try {
Files.copy(files.get(i).getInputStream(), TO);
} catch (IOException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}
socio.setLogo(pb);
}

//lo que hacemos es borrar el directrio antes de grabar un archivo

} 
/////////////////////////////////////////////fin de bloque si se borra el archivo grabamos solo si no existe
//////////////////////////si existe lo boirramos y grabamos


///if de control que no existe en la base de datos el nombre del archivo		
if(i==3) {// BannerEmpresa

File s4 = new File(directorio+"//s4");
if (!s4.exists()) {
if (s4.mkdirs()) {
System.out.println("Directorio creado"+s4);
} else {
System.out.println("Error al crear directorio");
}
}

String p1 = s4.toString();
Path p2 = Paths.get(p1);//creamos un PATH

////////////////////////////////////////////////////////////////////////////

Path TO = Paths.get(s4+"//"+socio.getEmpresa()+"I"+i+actualizar+"."+extension);

String p = TO.toString();

Path TOb = Paths.get("/files/"+socio.getId()+"_"+socio.getEmpresa()+"/s4/"+socio.getEmpresa()+"I"+i+actualizar+"."+extension);//sin todo el directorio
String pb = TOb.toString();

System.out.println(p);
///////////////////////////////////////////////// teniendo los datos coparamos que noexista ese nombre en la base de datos




System.out.println("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwww:");
final File[] files1 = s4.listFiles(); for (File f: files1) f.delete();
try {
Files.copy(files.get(i).getInputStream(), TO);
socio.setBaner_empresa(pb);
} catch (Exception e1) {
try {
Files.copy(files.get(i).getInputStream(), TO);
} catch (IOException e) {
//TODO Auto-generated catch block
e.printStackTrace();
}
socio.setBaner_empresa(pb);
}

//lo que hacemos es borrar el directrio antes de grabar un archivo

} 
/////////////////////////////////////////////fin de bloque si se borra el archivo grabamos solo si no existe
//////////////////////////si existe lo boirramos y grabamos



///if de control que no existe en la base de datos el nombre del archivo		
if(i==4) {// BannerSlogan

File s5 = new File(directorio+"//s5");
if (!s5.exists()) {
if (s5.mkdirs()) {
System.out.println("Directorio creado"+s5);
} else {
System.out.println("Error al crear directorio");
}
}

String p1 = s5.toString();
Path p2 = Paths.get(p1);//creamos un PATH

////////////////////////////////////////////////////////////////////////////

Path TO = Paths.get(s5+"//"+socio.getEmpresa()+"I"+i+actualizar+"."+extension);

String p = TO.toString();

Path TOb = Paths.get("/files/"+socio.getId()+"_"+socio.getEmpresa()+"/s5/"+socio.getEmpresa()+"I"+i+actualizar+"."+extension);//sin todo el directorio
String pb = TOb.toString();

System.out.println(pb);
///////////////////////////////////////////////// teniendo los datos coparamos que noexista ese nombre en la base de datos




System.out.println("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwww:");
final File[] files1 = s5.listFiles(); for (File f: files1) f.delete();
try {
Files.copy(files.get(i).getInputStream(), TO);
socio.setBaner_slogan(pb);
} catch (Exception e1) {
try {
Files.copy(files.get(i).getInputStream(), TO);
} catch (IOException e) {
//TODO Auto-generated catch block
e.printStackTrace();
}
socio.setBaner_slogan(pb);
}

//lo que hacemos es borrar el directrio antes de grabar un archivo

} 
/////////////////////////////////////////////fin de bloque si se borra el archivo grabamos solo si no existe
//////////////////////////si existe lo boirramos y grabamos



											

											
					}//if de extencion 
				
				
				
				
				
			//else de extension
				else {
					
								
				
						System.out.println("LA EXTENCION inavlida ES:"+extension);
						String p =".//src//main//resources//files//slider//1-2.jpg";
						//socio.setSlider1(p);
						
					
						
						}
				
				
				socioService.guardar(socio);
				
				
					 
				
				
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
				yahoo = new URI("http://localhost:8090/views/socios/");
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
	
	
	
	
	//////////////////////////////////////////////////////////////////////////////////////
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@GetMapping("/editE/{id}")
	public String editarE(@PathVariable("id") Long idSocio) {
		
		Socio socio= socioService.buscarPorId(idSocio);
				
		System.out.println("///////////////////////////////////////////////////////////////");
		long P=(socio.getId());
		System.out.println("el valor a edita es del codigo:"+P);
		
		if(socio.getEstado()==0) {
		socio.setEstado(3);}
		else {
			socio.setEstado(0);
		}
		socioService.guardar(socio);
		return "redirect:/views/socios/editA";
	}
	
	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") Long idSocio) {
		
		
		Socio socio= socioService.buscarPorId(idSocio);
				
		System.out.println("///////////////////////////////////////////////////////////////");
		long P=(socio.getId());
		System.out.println("Se borro:"+P);
		socio.setEstado(3);
		socioService.eliminar(idSocio);
		return "redirect:/views/socios/editA";
	}
	
	
	
	
}
