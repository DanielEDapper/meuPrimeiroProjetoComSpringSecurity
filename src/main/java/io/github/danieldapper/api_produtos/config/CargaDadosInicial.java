
package io.github.danieldapper.api_produtos.config;

import io.github.danieldapper.api_produtos.entity.Produto;
import io.github.danieldapper.api_produtos.entity.Role;
import io.github.danieldapper.api_produtos.entity.Usuario;
import io.github.danieldapper.api_produtos.repository.ProdutoRepository;
import io.github.danieldapper.api_produtos.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.List;

@Configuration
public class CargaDadosInicial {

    @Bean
    public CommandLineRunner carregarDados(
            ProdutoRepository produtoRepository,
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            // Cadastra os produtos iniciais
            if (produtoRepository.count() == 0) {

                List<Produto> produtos = List.of(
                        Produto.builder()
                                .nome("Notebook Dell Inspiron")
                                .preco(new BigDecimal("4500.00"))
                                .ativo(true)
                                .build(),

                        Produto.builder()
                                .nome("Mouse Gamer")
                                .preco(new BigDecimal("150.00"))
                                .ativo(true)
                                .build(),

                        Produto.builder()
                                .nome("Teclado Mecânico")
                                .preco(new BigDecimal("350.00"))
                                .ativo(true)
                                .build(),

                        Produto.builder()
                                .nome("Monitor 29")
                                .preco(new BigDecimal("1250.00"))
                                .ativo(true)
                                .build(),

                        Produto.builder()
                                .nome("Fone de ouvido bluetooth (descontinuado)")
                                .preco(new BigDecimal("200.00"))
                                .ativo(false)
                                .build()
                );

                produtoRepository.saveAll(produtos);
            }

            // Cadastra o administrador se ainda não existir
            if (usuarioRepository.findByLogin("user").isEmpty()) {

                Usuario admin = Usuario.builder()
                        .login("user")
                        .senha(passwordEncoder.encode("12345"))
                        .role(Role.ADMIN)
                        .build();

                usuarioRepository.save(admin);
            }
        };
    }
}