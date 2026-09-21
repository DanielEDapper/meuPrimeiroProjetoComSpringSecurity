package io.github.danieldapper.api_produtos.mapper;

import io.github.danieldapper.api_produtos.dto.ProdutoCreateRequest;
import io.github.danieldapper.api_produtos.dto.ProdutoResponse;
import io.github.danieldapper.api_produtos.dto.ProdutoUpdateRequest;
import io.github.danieldapper.api_produtos.entity.Produto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProdutoMapper
{
    /**
     * Converte os dados de criação para uma entidade Produto.
     * * @param request dados recebidos para criação
     * @return entidade Produto  */
    public Produto toEntity(ProdutoCreateRequest request){
        return Produto.builder()
                .nome(request.nome())
                .preco(request.preco())
                .build();
    }
    /**
     * Converte uma entidade Produto para o DTO de resposta.
     * * @param produto entidade persistida
     * @return representação pública do produto */
    public ProdutoResponse toResponse (Produto produto){
        return new ProdutoResponse(
                produto.getId(),
                produto.getNome(),
                produto.getPreco(),
                produto.getAtivo()
        );
    }
    /**Converte uma lista de Entidades pra uma lista de DTOs de resposta* */
    public List<ProdutoResponse> toResponseList(List<Produto> produtos){
        return produtos.stream()
                .map(this::toResponse)
                .toList();
    }
    public Produto updateEntity(ProdutoUpdateRequest request, Produto produto) {
        produto.setNome(request.nome());
        produto.setPreco(request.preco());
        produto.setAtivo(request.ativo());

        return produto;
    }

}
