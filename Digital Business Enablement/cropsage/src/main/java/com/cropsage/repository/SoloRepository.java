package com.cropsage.repository;

import com.cropsage.model.Solo;
import com.cropsage.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SoloRepository extends JpaRepository<Solo, Long> {

    List<Solo> findByUsuario(Usuario usuario);

}
