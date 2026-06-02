package com.rufinoguilherme.listaCompras.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rufinoguilherme.listaCompras.dto.requests.CategoriaRequestDTO;
import com.rufinoguilherme.listaCompras.dto.response.CategoriaResponseDTO;
import com.rufinoguilherme.listaCompras.entities.Categoria;
import com.rufinoguilherme.listaCompras.service.CategoriaService.CategoriaService;
import com.rufinoguilherme.listaCompras.service.ProdutoService.ProdutoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

	//@Autowired CategoriaService categoriaService;
	
	private final CategoriaService categoriaService;
	
	private final ProdutoService produtoService;
	public CategoriaController(CategoriaService categoriaService, ProdutoService produtoService) {
		this.categoriaService = categoriaService;
		this.produtoService = produtoService;
	}
	
	
	@PostMapping
	public ResponseEntity<CategoriaResponseDTO> createCategoria (@Valid @RequestBody CategoriaRequestDTO categoriaRequestDTO){
		
		CategoriaResponseDTO categoriaResponseDTO =  categoriaService.create(categoriaRequestDTO);	
		
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaResponseDTO);
	}
	
	@GetMapping
	public ResponseEntity<List<Categoria>> getAllCategorias(){
		
		List<Categoria> categoriasResponseDTOs =  categoriaService.getAllCategorias();
		
		return ResponseEntity.ok(categoriasResponseDTOs);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<CategoriaResponseDTO> update(@PathVariable Long id, @Valid @RequestBody CategoriaRequestDTO categoriaRequestDTO) {
		
		CategoriaResponseDTO categoriaResponseDTO  =  categoriaService.update(id,categoriaRequestDTO);
		
		return ResponseEntity.ok(categoriaResponseDTO);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		
		categoriaService.delete(id);
		
		return ResponseEntity.noContent().build();
	}
	
	
	@DeleteMapping("/{categoriaId}/produtos")
	public ResponseEntity<Void> deleteProdutosDaCategoria(
	        @PathVariable Long categoriaId) {

	    produtoService.deleteByCategoria(categoriaId);

	    return ResponseEntity.noContent().build();
	}
	
	
}
