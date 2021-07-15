package com.gameStore.minhaGameStore.repository;

import java.util.List;

import com.gameStore.minhaGameStore.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

	public List<Categoria> findByNomeContainingIgnoreCase(String nome);

}
