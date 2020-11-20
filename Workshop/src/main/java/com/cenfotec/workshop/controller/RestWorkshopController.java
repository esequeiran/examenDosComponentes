package com.cenfotec.workshop.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cenfotec.workshop.domain.HibernateProxyTypeAdapter;
import com.cenfotec.workshop.domain.Workshop;
import com.cenfotec.workshop.service.ActividadService;
import com.cenfotec.workshop.service.CategoriaService;
import com.cenfotec.workshop.service.WorkshopService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController
@RequestMapping("/api")
public class RestWorkshopController {

	@Autowired
	WorkshopService workshopService;
	
	
	//MAPEO A LISTAR WORKSHOP
	
				
			@GetMapping("/taller")
			@ResponseBody
			public void jsonWorkshopDataTable(HttpServletRequest request, HttpServletResponse response,Model model) throws IOException {
				List<Workshop> list= workshopService.getAll();
				GsonBuilder b = new GsonBuilder();
				b.registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY);
				System.out.println("ya cree b gsonbuilder");
				String jsonString = b.create().toJson(list);	
				    
				    
			  		response.setContentType(jsonString);  
			}
			
}
