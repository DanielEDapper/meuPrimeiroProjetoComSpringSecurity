package io.github.danieldapper.api_produtos.service;

import io.github.danieldapper.api_produtos.dto.ProdutoCreateRequest;
import io.github.danieldapper.api_produtos.dto.ProdutoResponse;
import io.github.danieldapper.api_produtos.dto.ProdutoUpdateRequest;
import io.github.danieldapper.api_produtos.entity.Produto;
import io.github.danieldapper.api_produtos.exception.ProdutoNaoEncontradoException;
import io.github.danieldapper.api_produtos.mapper.ProdutoMapper;
import io.github.danieldapper.api_produtos.repository.ProdutoRepository;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProdutoService
{
    private final ProdutoRepository repository;
    private final ProdutoMapper mapper;

    public ProdutoService(ProdutoRepository repository, ProdutoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    /**
     * Cadastra um novo produto na base de dados após validar a unicidade do nome
     * @param request Objeto contendo os dados de entrada para criação do produto
     * @return DTO {@link ProdutoResponse} com os dados do produto persistido
     * @throws IllegalArgumentException Se já existir um produto cadastrado com o mesmo nome
     * */
    @Transactional
    public ProdutoResponse cadastrar(ProdutoCreateRequest request){
        if(repository.existsByNomeIgnoreCase(request.nome())){
            throw new IllegalArgumentException("Já existe um produto com esse nome");
        }
        //converte DTO -> Entity
        Produto produto = mapper.toEntity(request);
        produto.setAtivo(true);
        Produto salvo = repository.save(produto);

        //Converte Entity -> DTO Response
        return mapper.toResponse(salvo);
    }

    /**
     * Retorna todos os produtos cadastrados
     * @return Lista de DTOs {@link ProdutoResponse} representando os produtos encontrados.Lista vazia caso nenhum produto seja encontrado
     */
    @Transactional(readOnly = true)
    public List<ProdutoResponse> listar()
    {
        List<Produto> produtos = repository.findAll();

        return mapper.toResponseList(produtos);
    }

    @Transactional(readOnly = true)
    public ProdutoResponse buscarPorId(Long id)
    {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new
                        ProdutoNaoEncontradoException("Produto não encontrado com o ID: "+id));
    }

    @Transactional(readOnly = true)
    public List<ProdutoResponse> buscarPorNome(String nome)
    {
        return repository.findByNomeContainingIgnoreCase(nome)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Transactional
    public ProdutoResponse atualizar(Long id, ProdutoUpdateRequest request)
    {
        Produto produto = repository.findById(id)
                .orElseThrow(() -> new
                        ProdutoNaoEncontradoException("Produto não encontrado com o ID:" + id));

        Produto produtoAtt = mapper.updateEntity(request, produto);
        Produto atualizado = repository.save(produtoAtt);

        return mapper.toResponse(atualizado);
    }

    public void remover(Long id)
    {
        Produto produto = repository.findById(id)
                .orElseThrow(() -> new
                        ProdutoNaoEncontradoException("Produto não encontrado com ID: "+id));

        repository.delete(produto);
    }
}
