package com.rufinoguilherme.listaCompras.dto.requests;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProdutoRequestDTO(
		@NotBlank(message = "nome obrigatprio")
		String nome,
		@NotNull(message = "categoria obrigatória")
		Long categoria
) {

}
