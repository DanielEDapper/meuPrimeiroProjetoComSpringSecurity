package io.github.danieldapper.api_produtos.dto;

import java.math.BigDecimal;

public record ProdutoUpdateRequest(String nome, BigDecimal preco, Boolean ativo) {
}
