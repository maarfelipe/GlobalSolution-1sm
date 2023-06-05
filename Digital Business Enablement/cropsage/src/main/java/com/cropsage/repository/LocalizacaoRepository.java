package com.cropsage.repository;

import com.cropsage.model.Localizacao;
import com.cropsage.model.Solo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalizacaoRepository extends JpaRepository<Localizacao, Long> {
}
