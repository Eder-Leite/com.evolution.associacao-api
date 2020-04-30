package com.evolution.repository.adm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.evolution.model.adm.Produto;
import com.evolution.repository.adm.produto.ProdutoRepositoryQuery;

@Transactional
public interface ProdutoRepository extends JpaRepository<Produto, Long>, ProdutoRepositoryQuery {

}
