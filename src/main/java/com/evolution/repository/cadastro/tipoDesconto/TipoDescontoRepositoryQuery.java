package com.evolution.repository.cadastro.tipoDesconto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.evolution.repository.cadastro.filter.TipoDescontoFilter;
import com.evolution.repository.cadastro.projection.TipoDescontoResumo;

public interface TipoDescontoRepositoryQuery {

	public Page<TipoDescontoResumo> resumir(TipoDescontoFilter tipoDescontoFilter, Pageable pageable);

}
