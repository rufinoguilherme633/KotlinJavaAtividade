package com.rufinoguilherme.listaCompras.exception;

public record ErrorResponse(
		Integer status,
        String message
){

}
