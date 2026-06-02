package com.rufinoguilherme.listaCompras.dto.response;

public record ProdutoCheckedResponseDTO(
		Long id,
	    String nome,
	    boolean checked
		) {
	
}
