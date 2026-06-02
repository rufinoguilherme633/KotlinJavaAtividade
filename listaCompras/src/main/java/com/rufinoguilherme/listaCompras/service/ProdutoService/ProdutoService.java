package com.rufinoguilherme.listaCompras.service.ProdutoService;


import java.util.List;

import org.springframework.stereotype.Service;

import com.rufinoguilherme.listaCompras.controller.ProdutoCheckedRequestDTO;
import com.rufinoguilherme.listaCompras.dto.requests.ProdutoRequestDTO;
import com.rufinoguilherme.listaCompras.dto.response.CategoriaResponseDTO;
import com.rufinoguilherme.listaCompras.dto.response.ProdutoCheckedResponseDTO;
import com.rufinoguilherme.listaCompras.dto.response.ProdutoResponseDTO;
import com.rufinoguilherme.listaCompras.entities.Categoria;
import com.rufinoguilherme.listaCompras.entities.Produto;
import com.rufinoguilherme.listaCompras.exception.BusinessException;
import com.rufinoguilherme.listaCompras.exception.ResourceNotFoundException;
import com.rufinoguilherme.listaCompras.repository.CategoriaRepository;
import com.rufinoguilherme.listaCompras.repository.ProdutoRepository;

import jakarta.validation.Valid;


@Service
public class ProdutoService {

	
	private final ProdutoRepository produtoRepository;
	private final CategoriaRepository categoriaRepository;
	
	
	public ProdutoService(ProdutoRepository produtoRepository,CategoriaRepository categoriaRepository) {
		this.produtoRepository = produtoRepository;
		this.categoriaRepository = categoriaRepository;
	}
	
	public ProdutoResponseDTO create(ProdutoRequestDTO produtoRequestDTO) {
		
		
		Categoria categoria = categoriaRepository.findById(produtoRequestDTO.categoria()).orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));
		
		if(produtoRepository.existsByNomeAndCategoriaId(produtoRequestDTO.nome(), produtoRequestDTO.categoria())) {
			
			throw new BusinessException("Produto '" + produtoRequestDTO.nome() +
				    "' já cadastrado nesta categoria");
		}
		
		Produto produto = new Produto();
		
		produto.setNome(produtoRequestDTO.nome());
		produto.setCategoria(categoria);
		produto.setChecked(false);
		produto = produtoRepository.save(produto);
		
		ProdutoResponseDTO produtoResponseDTO = new ProdutoResponseDTO(produto.getId(), produto.getNome(), produto.getCategoria().getId());
		
		return produtoResponseDTO;
	}

	public List<ProdutoCheckedResponseDTO> getAllProdutos(Long categoriaId) {
		
		
		List<Produto> produtos = produtoRepository.findAllByCategoriaId(categoriaId);
		
		
		return  produtos.stream()
	            .map(produto -> new ProdutoCheckedResponseDTO(
	                    produto.getId(),
	                    produto.getNome(),
	                    produto.isChecked()
	                    ))
	            .toList();
	}

	public ProdutoCheckedResponseDTO update(Long id, @Valid ProdutoCheckedRequestDTO produtoCheckedRequestDTO) {
		
		Produto produto  =  produtoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Nenhum registro encontrado"));
		
		produto.setNome(produtoCheckedRequestDTO.nome());	
		produto.setChecked(produtoCheckedRequestDTO.checked());
		produtoRepository.save(produto);

		ProdutoCheckedResponseDTO categoriaResponseDTO = new ProdutoCheckedResponseDTO(produto.getId(), produto.getNome(),produto.isChecked());
			
		return categoriaResponseDTO;
	}

	public void delete(Long id) {
		produtoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Nenhum registro encontrado"));
		
		
		produtoRepository.deleteById(id);	
		
	}

	public void deleteByCategoria(Long categoriaId) {
		if (!categoriaRepository.existsById(categoriaId)) {
	        throw new ResourceNotFoundException("Categoria não encontrada");
	    }
		
		produtoRepository.deleteByCategoriaId(categoriaId);
		
	}

	
	
}
