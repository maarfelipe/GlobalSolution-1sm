package com.cropsage.repository;

import com.cropsage.model.Produto;
import com.cropsage.model.Solo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
