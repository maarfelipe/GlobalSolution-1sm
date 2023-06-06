package com.cropsage.repository;

import com.cropsage.model.Solo;
import com.cropsage.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Object> findByEmail(String username);
}
