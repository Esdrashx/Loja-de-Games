package com.gameStore.minhaGameStore.repository;

import java.util.List;

import com.gameStore.minhaGameStore.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

	public List<Produto> findAllByTituloContainingIgnoreCase(String titulo);
}
