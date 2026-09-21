package io.github.danieldapper.api_produtos.exception;

import io.github.danieldapper.api_produtos.dto.ErroResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler(ProdutoNaoEncontradoException.class)
    public ResponseEntity<ErroResponse> tratarProdutoNaoEncontrado(
            ProdutoNaoEncontradoException  ex, HttpServletRequest request)
    {
        ErroResponse erro = ErroResponse.criar(
                HttpStatus.NOT_FOUND.value(),
                "Recurso não encontrado",
                ex.getMessage(),
                request.getRequestURI()
        );


        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }


}
