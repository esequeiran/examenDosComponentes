package com.cenfotec.workshop.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cenfotec.workshop.domain.Categoria;
import com.cenfotec.workshop.service.CategoriaService;

@Controller
public class CategoriaController {
	
	@Autowired
	CategoriaService categoriaService;
	
	//MAPEO A HOME
	@RequestMapping("/")
	public String home(Model model) {
		return "index";
	}
	
	//MAPEO A INSERTAR CATEGORIA
	@RequestMapping(value = "/insertarCategoria",  method = RequestMethod.GET)
	public String insertarPage(Model model) {
		model.addAttribute(new Categoria());
		return "insertarCategoria";
	}	
	
	@RequestMapping(value = "/insertarCategoria",  method = RequestMethod.POST)
	public String insertarAction(Categoria categoria, BindingResult result, Model model) {
		categoriaService.save(categoria);
		return "index";
	}
		
	
	//MAPEO A MODIFICAR CATEGORIA
	@GetMapping("/modificarCategoria/{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {
	    Categoria categoria = categoriaService.get(id)
	      .orElseThrow(() -> new IllegalArgumentException("Categoria inválida:" + id));
	    
	    model.addAttribute("categoria", categoria);
	    return "modificarCategoria";
	}
	
	@PostMapping("/modificarCategoria/{id}")
	public String modificarCategoria(@PathVariable("id") long id, Categoria categoria, 
	  BindingResult result, Model model) {
	    if (result.hasErrors()) {
	    	categoria.setId(id);
	        return "modificarCategoria";
	    }
	        
	    categoriaService.save(categoria);
	    model.addAttribute("categoria", categoriaService.getAll());
	    return "index";
	}
	
	
	//MAPEO A LISTAR CATEGORIAS
	@RequestMapping("/listarCategoria")
	public String listarCategoria(Model model) {
		model.addAttribute("categorias",categoriaService.getAll());
		return "listarCategoria";
	}
	
	
	//MAPEO A ELIMINAR CATEGORIAS
	@GetMapping("/eliminarCategoria/{id}")
	public String eliminarCategoria(@PathVariable("id") long id, Model model) {
	    Optional<Categoria> categoria = categoriaService.get(id);
	    if(categoria.isPresent()) {
	    	categoriaService.deleteCategoria(id);
	    	return "index";
	    }	      
	    return "error";
	}
	

}
