package com.evolution.repository.adm.produto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.evolution.repository.adm.filter.ProdutoFilter;
import com.evolution.repository.adm.projection.ProdutoResumo;


public interface ProdutoRepositoryQuery {

	public Page<ProdutoResumo> resumir(ProdutoFilter produtoFilter, Pageable pageable);

}
