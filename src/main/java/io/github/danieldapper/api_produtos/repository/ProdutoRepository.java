package io.github.danieldapper.api_produtos.repository;

import io.github.danieldapper.api_produtos.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>
{
    boolean existsByNomeIgnoreCase(String nome);

    List<Produto> findByNomeContainingIgnoreCase(String nome);
}
