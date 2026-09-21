package io.github.danieldapper.api_produtos.exception;

public class ProdutoNaoEncontradoException extends RuntimeException {
    public ProdutoNaoEncontradoException(String message) {
        super(message);
    }
}