package com.rufinoguilherme.listaCompras.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.rufinoguilherme.listaCompras.entities.Produto;

import jakarta.validation.constraints.NotBlank;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{

	boolean existsByNomeAndCategoriaId(String nome,Long categoriaId);

	List<Produto> findAllByCategoriaId(Long categoriaId);
	
	@Transactional
	@Modifying
	@Query("DELETE FROM Produto p WHERE p.categoria.id = :categoriaId")
	void deleteByCategoriaId(@Param("categoriaId") Long categoriaId);

}
