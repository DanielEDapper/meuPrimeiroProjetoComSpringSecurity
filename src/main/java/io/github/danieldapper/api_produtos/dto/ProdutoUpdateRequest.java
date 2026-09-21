package io.github.danieldapper.api_produtos.dto;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

public record ProdutoUpdateRequest(String nome, BigDecimal preco, Boolean ativo) {
}
