package com.guilhermerufino.listacompras.data.api

import com.guilhermerufino.listacompras.data.model.Categoria
import com.guilhermerufino.listacompras.data.model.CategoriaRequest
import com.guilhermerufino.listacompras.data.model.Produto
import com.guilhermerufino.listacompras.data.model.ProdutoRequest
import com.guilhermerufino.listacompras.data.model.ProdutoRequestUpdate
import retrofit2.Response  // 👈 era okhttp3.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CategoriaApi {


    @POST("categorias")
    suspend fun salvar(@Body categoria : CategoriaRequest)

    @GET("categorias")
    suspend fun listarCategorias(): List<Categoria>

    @DELETE("categorias/{id}")
    suspend fun deletarCategoria(@Path("id") id: Long): Response<Unit>

    @PUT("categorias/{id}")
    suspend fun update(@Path("id") id: Long,@Body categoria : CategoriaRequest)



    @GET("produtos/categoria/{id}")
    suspend fun listarProdutos(@Path("id") id: Long): List<ProdutoRequestUpdate>


    @POST("produtos")
    suspend fun salvarProdutos(@Body produto : ProdutoRequest )


    @PUT("produtos/{id}")
    suspend fun updateProduto(@Path("id") id: Long,@Body produto : ProdutoRequestUpdate): Response<ProdutoRequestUpdate>

    @DELETE("produtos/{id}")
    suspend fun deletarProduto(@Path("id") id: Long): Response<Unit>

}