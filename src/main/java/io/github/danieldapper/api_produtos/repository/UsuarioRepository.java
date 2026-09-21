package io.github.danieldapper.api_produtos.repository;

import io.github.danieldapper.api_produtos.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>
{
    Optional<Usuario> findByLogin(String login);
}