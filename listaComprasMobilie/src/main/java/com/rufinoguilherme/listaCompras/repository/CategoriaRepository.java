package com.rufinoguilherme.listaCompras.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.rufinoguilherme.listaCompras.entities.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

	Optional<Categoria>  findByNome(String nome);
	
	boolean existsByNome(String nome);

}
