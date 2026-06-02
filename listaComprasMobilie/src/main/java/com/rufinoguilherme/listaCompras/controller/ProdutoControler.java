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
import com.rufinoguilherme.listaCompras.dto.requests.ProdutoRequestDTO;
import com.rufinoguilherme.listaCompras.dto.response.CategoriaResponseDTO;
import com.rufinoguilherme.listaCompras.dto.response.ProdutoCheckedResponseDTO;
import com.rufinoguilherme.listaCompras.dto.response.ProdutoResponseDTO;
import com.rufinoguilherme.listaCompras.entities.Categoria;
import com.rufinoguilherme.listaCompras.entities.Produto;
import com.rufinoguilherme.listaCompras.service.ProdutoService.ProdutoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/produtos")
public class ProdutoControler {
	
	private final ProdutoService produtoService;
	
	
	public ProdutoControler(ProdutoService produtoService) {
		this.produtoService = produtoService;
	}
	
	
	@PostMapping
	public ResponseEntity<ProdutoResponseDTO> createProduto (@Valid @RequestBody ProdutoRequestDTO produtoRequestDTO){
		
		ProdutoResponseDTO produtoResponseDTO =  produtoService.create(produtoRequestDTO);	
		
		return ResponseEntity.status(HttpStatus.CREATED).body(produtoResponseDTO);
	}
	
	
	@GetMapping("/categoria/{categoriaId}")
	public ResponseEntity<List<ProdutoCheckedResponseDTO>> getAllProduto(@PathVariable Long categoriaId){
		
		List<ProdutoCheckedResponseDTO> produtoResponseDTOs =  produtoService.getAllProdutos(categoriaId);
		
		return ResponseEntity.ok(produtoResponseDTOs);
	}

	
	@PutMapping("/{id}")
	public ResponseEntity<ProdutoCheckedResponseDTO> update(@PathVariable Long id, @Valid @RequestBody ProdutoCheckedRequestDTO produtoCheckedRequestDTO) {
		
		ProdutoCheckedResponseDTO produtoResponseDTO  =  produtoService.update(id,produtoCheckedRequestDTO);
		
		return ResponseEntity.ok(produtoResponseDTO);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		
		produtoService.delete(id);
		
		return ResponseEntity.noContent().build();
	}
	
}
