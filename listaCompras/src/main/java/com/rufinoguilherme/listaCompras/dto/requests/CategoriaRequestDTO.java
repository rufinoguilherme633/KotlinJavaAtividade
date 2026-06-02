package com.rufinoguilherme.listaCompras.dto.requests;

import jakarta.validation.constraints.NotBlank;

public record CategoriaRequestDTO(
		
		@NotBlank(message= "Nome obrigatório")
		String nome
		
		) {

}
