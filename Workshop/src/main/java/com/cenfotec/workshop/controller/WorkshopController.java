package com.cenfotec.workshop.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.cenfotec.workshop.domain.Actividad;
import com.cenfotec.workshop.domain.Categoria;
import com.cenfotec.workshop.domain.Workshop;
import com.cenfotec.workshop.service.ActividadService;
import com.cenfotec.workshop.service.CategoriaService;
import com.cenfotec.workshop.service.WorkshopService;
import com.google.gson.Gson;



@Controller
public class WorkshopController {

	@Autowired
	WorkshopService workshopService;
	
	@Autowired
	CategoriaService categoriaService;
	
	@Autowired
	ActividadService actividadService;
	
	//MAPEO A INSERTAR WORKSHOP
	@RequestMapping(value = "/insertarWorkshop",  method = RequestMethod.GET)
	public String insertarPage(Model model) {
		model.addAttribute(new Workshop());
		model.addAttribute("categorias", categoriaService.getAll());
		return "insertarWorkshop";
	}	
	
	@RequestMapping(value = "/insertarWorkshop",  method = RequestMethod.POST)
	public String insertarAction(Workshop workshop, BindingResult result, Model model) {
		Optional<Categoria> categoria = categoriaService.get(workshop.getIdCategoria());
		if(categoria.isPresent()) {
			workshop.setCategoria(categoria.get());
			workshopService.save(workshop);
			return "index";		}
		return "error";
		
	}
			
	
	//MAPEO A LISTAR WORKSHOP
	
	@RequestMapping("/listarWorkshop")
	public String listarWorkshop(Model model) {
		model.addAttribute("workshops",workshopService.getAll());
		return "listarWorkshop";
	}
	
	//MAPEO A LISTAR WORKSHOP POR CATEGORIA
	@RequestMapping(value="/listarWorkshopByCategoria", method = RequestMethod.GET)
	public String insertarPagelistarWorkshopCategoria(Model model){	
		model.addAttribute("workshops", null);
		model.addAttribute(new Categoria());
		model.addAttribute("categorias", categoriaService.getAll());
		return "listarWorkshopByCategoria";
	}
	
	
	@RequestMapping(value="/listarWorkshopByCategoria",  method = RequestMethod.POST)
	public String listarWorkshopByCategoria(Categoria categoria, BindingResult resultModel,Model model) {

		model.addAttribute("workshops",workshopService.findByCategoria(categoria.getId()));
		model.addAttribute("categorias", categoriaService.getAll());
		return "listarWorkshopByCategoria";
	}
	
	//MAPEO A LISTAR WORKSHOP POR PALABRA CLAVE
	@RequestMapping(value="/listarWorkshopByPalabraClave", method = RequestMethod.GET)
	public String insertarPagelistarWorkshopByPalabraClave(Model model) {
		model.addAttribute("workshops", null);	
		model.addAttribute(new Workshop());	
		return "listarWorkshopByPalabraClave";
	}
	
	@RequestMapping(value="/listarWorkshopByPalabraClave",  method = RequestMethod.POST)
	public String listarWorkshopByPalabraClave(Workshop workshop, BindingResult resultModel, Model model) {
		model.addAttribute("workshops",workshopService.findByPalabrasClave(workshop.getPalabrasClave()));		
		return "listarWorkshopByPalabraClave";
	}
	
	//MAPEO A LISTAR WORKSHOP POR AUTOR
	@RequestMapping(value="/listarWorkshopByAutor", method = RequestMethod.GET)
	public String insertarPagelistarWorkshopAutor(Model model){	
		model.addAttribute("workshops", null);
		model.addAttribute(new Workshop());		
		return "listarWorkshopByAutor";
	}
	
	@RequestMapping(value="/listarWorkshopByAutor",  method = RequestMethod.POST)
	public String listarWorkshopByAutor(Workshop workshop, BindingResult resultModel, Model model) {
		model.addAttribute("workshops",workshopService.findByAutor(workshop.getAutor()));		
		return "listarWorkshopByAutor";
	}
	
	@RequestMapping(value="/insertarActividad/{id}")
	public String recoverForAddActividad(Model model, @PathVariable long id) {
		Optional<Workshop> workshop = workshopService.get(id);
		Actividad newActividad = new Actividad();
		if (workshop.isPresent()) {
			newActividad.setWorkshop(workshop.get());
			model.addAttribute("workshop",workshop.get());
			model.addAttribute("actividad",newActividad);
			return "insertarActividad";	
		}
		return "notfound";
	}
	
	@RequestMapping(value="/insertarActividad/{id}", method = RequestMethod.POST)
	public String saveActividad(Actividad actividad, Model model, @PathVariable long id) {
		Optional<Workshop> workshop = workshopService.get(id);
		if (workshop.isPresent()) {
			
			Workshop ws = workshop.get();
			if(ws.getTiempoDuracion() == null) {
				ws.setTiempoDuracion(actividad.getTiempoDuracion());
			}else {
				ws.setTiempoDuracion(ws.getTiempoDuracion() + actividad.getTiempoDuracion());
				workshopService.save(ws);
			}
	
			
			actividad.setWorkshop(workshop.get());
			actividad.setFechaPublicacion(LocalDate.now());
			actividadService.save(actividad);
			return "index";
		}
		return "error";		
	}	
	
	//MAPEO A MODIFICAR WORKSHOP
	@GetMapping("/modificarWorkshop/{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {
	    Workshop workshop = workshopService.get(id)
	      .orElseThrow(() -> new IllegalArgumentException("Workshop inválido:" + id));
	    model.addAttribute("categorias", categoriaService.getAll());
	    model.addAttribute("workshop", workshop);
	    return "modificarWorkshop";
	}
	
	@PostMapping("/modificarWorkshop/{id}")
	public String modificarCategoria(@PathVariable("id") long id, Workshop workshop, 
	  BindingResult result, Model model) {
	    if (result.hasErrors()) {
	    	workshop.setId(id);
	        return "modificarWorkshop";
	    }
	    
	    Optional<Categoria> categoria = categoriaService.get(workshop.getIdCategoria());
		if(categoria.isPresent()) {
			workshop.setCategoria(categoria.get());
			workshopService.save(workshop);
			model.addAttribute("workshop", workshopService.getAll());
			return "index";		
		}else {
			return "error";
		}	       
	    
	   
	}
	
	//MAPEO A GENERAR WORD
	@RequestMapping(value = "/generarWord/{id}", method= RequestMethod.GET)
	@ResponseBody
	public void downloadWord(HttpServletRequest request, HttpServletResponse response,@PathVariable("id") long id, Model model) throws IOException {
	    Workshop workshop = workshopService.get(id)
	      .orElseThrow(() -> new IllegalArgumentException("Workshop inválido:" + id));
	   	    
	    XWPFDocument document = new XWPFDocument();
	    //Nombre del documento, título
	    XWPFParagraph title = document.createParagraph();
	    title.setAlignment(ParagraphAlignment.CENTER);
	    
	    XWPFRun titleRun = title.createRun();
	    titleRun.setText("Workshop "+workshop.getNombre()+".");
	    titleRun.setColor("000033");
	    titleRun.setBold(true);
	    titleRun.setFontFamily("Courier");
	    titleRun.setFontSize(20);
	    
	    //parte del encabezado, subtítulo con tiempo de duración
	    XWPFParagraph subTitle = document.createParagraph();
	    subTitle.setAlignment(ParagraphAlignment.CENTER);
	    
	    XWPFRun subTitleRun = subTitle.createRun();
	    String time = "";
	    if(workshop.getTiempoDuracion() == null) {
	    	time = "no hay actividades registradas";
	    }else {
	    	time = workshop.getTiempoDuracion().toString();
	    }
	    subTitleRun.setText("Tiempo de duración: "+time+" minutos");
	    subTitleRun.setColor("0000CC");
	    subTitleRun.setFontFamily("Courier");
	    subTitleRun.setFontSize(16);
	    subTitleRun.setTextPosition(20);
	    subTitleRun.setUnderline(UnderlinePatterns.DOT_DOT_DASH);
	    
	    
	    XWPFParagraph para1 = document.createParagraph();
	    para1.setAlignment(ParagraphAlignment.BOTH);	    
	    XWPFRun para1Run = para1.createRun();
	    
	    para1Run.setText("Autor: "+workshop.getAutor()+"\n"+
	    				"Objetivo: "+workshop.getObjetivo()+"\n"+
	    				"Palabras clave: "+workshop.getPalabrasClave()+"\n"+
	    				"Categoría: "+workshop.getCategoria().getNombre()+"\n");
	    para1Run.setColor("004C99");
	    
	    XWPFParagraph subTitleActividades = document.createParagraph();
	    subTitleActividades.setAlignment(ParagraphAlignment.CENTER);	    
	    XWPFRun subTitleActividadesRun = subTitleActividades.createRun();
	    subTitleActividadesRun.setText("Actividades del workshop");
	    subTitleActividadesRun.setColor("009999");
	    subTitleActividadesRun.setFontFamily("Courier");
	    subTitleActividadesRun.setFontSize(16);
	    subTitleActividadesRun.setTextPosition(20);
	    subTitleActividadesRun.setUnderline(UnderlinePatterns.DOT_DOT_DASH);
	    
	    
	    XWPFParagraph actividadesPara = document.createParagraph();
	    actividadesPara.setAlignment(ParagraphAlignment.BOTH);	    
	    XWPFRun actividadesParaRun = actividadesPara.createRun();
	    String acti = "";
	    for(Actividad act: workshop.getActividades()) {
	    	acti += "Nombre: "+act.getNombre()+"\n"+
	    			"Descripción: "+act.getDescripcion()+"\n"+
	    			"Texto mostrado/leído: "+act.getTexto()+"\n"+
	    			"Duración en minutos: "+act.getTiempoDuracion()+"\n"+
	    			"*******************************************************************"+"\n";
	    }
	    actividadesParaRun.setText(acti);
	    actividadesParaRun.setColor("404040");
	   
	    response.setHeader("Content-disposition", "attachment; filename="+workshop.getId()+".docx");
		  		document.write(response.getOutputStream());	   
	}
	
	@RequestMapping(value="/datatableWorkshop", method=  RequestMethod.GET)
	public String listarWorkshopDataTable(Model model) {
		model.addAttribute("workshops",workshopService.getAll());
		/*
		String url = "http://localhost:8080/api/taller";
		
		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(url, String.class);
		System.out.println(result);
		*/
		return "datatableWorkshop";
	}
	
	/*
	
	@RequestMapping(value="/datatableWorkshop/json", method=  RequestMethod.GET)
	@ResponseBody
	public void jsonWorkshopDataTable(HttpServletRequest request, HttpServletResponse response,Model model) throws IOException {
		
		
		String jsonString = new Gson().toJson(list);
		model.addAttribute("workshops",list);
		
		 XWPFDocument document = new XWPFDocument();
		    //Nombre del documento, título
		    XWPFParagraph title = document.createParagraph();
		    title.setAlignment(ParagraphAlignment.CENTER);
		    
		    XWPFRun titleRun = title.createRun();
		    titleRun.setText("Lista de workshops en formato Json");
		    titleRun.setColor("000033");
		    titleRun.setBold(true);
		    titleRun.setFontFamily("Courier");
		    titleRun.setFontSize(20);
		
		    XWPFParagraph para1 = document.createParagraph();
		    para1.setAlignment(ParagraphAlignment.BOTH);	    
		    XWPFRun para1Run = para1.createRun();
		    
		    para1Run.setText(jsonString+"\n");
		    para1Run.setColor("004C99");
		    
		    response.setHeader("Content-disposition", "attachment; filename=workshopJson.docx");
	  		document.write(response.getOutputStream());	  
	  		
	}
	*/
}
