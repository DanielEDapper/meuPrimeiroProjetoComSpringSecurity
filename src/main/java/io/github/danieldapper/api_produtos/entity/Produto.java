package io.github.danieldapper.api_produtos.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Representa um produto persistido pela aplicação
 * <p> Esta entidade contém os dados internos utilizados para camada de persistência</p>
 * */

@Entity
@Table(name = "tb_produto")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false)
    private BigDecimal preco;

    @Column(nullable = false)
    private Boolean ativo;
}