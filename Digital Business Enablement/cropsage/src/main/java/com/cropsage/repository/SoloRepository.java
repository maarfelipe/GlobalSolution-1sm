package com.cropsage.repository;

import com.cropsage.model.Localizacao;
import com.cropsage.model.Solo;
import com.cropsage.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface SoloRepository extends JpaRepository<Solo, Long> {
    @Query("select t from T_GS_SOLO t where t.usuario = ?1")
    List<Solo> findByUsuario(Usuario usuario);
    @Query("select t from T_GS_SOLO t where t.usuario = ?1 and upper(t.produto.nome) like upper(concat('%', ?2, '%'))")
    List<Solo> findByUsuarioAndProduto_NomeContainsIgnoreCase(Usuario usuario, String nome);
    @Query("""
            select t from T_GS_SOLO t
            where t.usuario = ?1 and upper(t.localizacao.nome) like upper(concat('%', ?2, '%'))""")
    List<Solo> findByUsuarioAndLocalizacao_NomeContainsIgnoreCase(Usuario usuario, String nome);

    //List<Solo> findByUsuario(Usuario usuario);
    //List<Solo> findByUsuarioAndProdutoNomeContaining(Usuario usuario, String nome);
    //List<Solo> findByUsuarioAndLocalizacaoNomeContaining(Usuario usuario, String nome);

}
