package com.rufinoguilherme.listaCompras.service.CategoriaService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rufinoguilherme.listaCompras.dto.requests.CategoriaRequestDTO;
import com.rufinoguilherme.listaCompras.dto.response.CategoriaResponseDTO;
import com.rufinoguilherme.listaCompras.entities.Categoria;
import com.rufinoguilherme.listaCompras.exception.BusinessException;
import com.rufinoguilherme.listaCompras.exception.ResourceNotFoundException;
import com.rufinoguilherme.listaCompras.repository.CategoriaRepository;
import jakarta.validation.Valid;

@Service
public class CategoriaService {

	//@Autowired CategoriaRepository categoriaRepository;
	
	
	
	private final CategoriaRepository categoriaRepository;
	
	public CategoriaService(CategoriaRepository categoriaRepository) {
		this.categoriaRepository = categoriaRepository;
	}
	
	public CategoriaResponseDTO create(CategoriaRequestDTO categoriaRequestDTO) {
	
		if(categoriaRepository.existsByNome(categoriaRequestDTO.nome())) {
			
			throw new BusinessException("Categoria '" + categoriaRequestDTO.nome() + "' já cadastrada");
		}
		
		Categoria categoria = new Categoria();
		
		categoria.setNome(categoriaRequestDTO.nome());
		
		 categoriaRepository.save(categoria);
		
		CategoriaResponseDTO categoriaResponseDTO = new CategoriaResponseDTO(categoria.getId(), categoria.getNome());
		
		return categoriaResponseDTO;
	}

	public List<Categoria> getAllCategorias() {
			
		return categoriaRepository.findAll();
	}

	public CategoriaResponseDTO update(Long id, @Valid CategoriaRequestDTO categoriaRequestDTO) {
	
		Categoria categoria  =  categoriaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Nenhum registro encontrado"));
		
		categoria.setNome(categoriaRequestDTO.nome());		
		categoriaRepository.save(categoria);

		CategoriaResponseDTO categoriaResponseDTO = new CategoriaResponseDTO(categoria.getId(), categoria.getNome());
			
		return categoriaResponseDTO;
	}

	
	public void delete(Long id) {
		
		categoriaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Nenhum registro encontrado"));
		
		
		categoriaRepository.deleteById(id);					
	}

}
