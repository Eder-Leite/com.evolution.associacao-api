package com.evolution.repository.adm.movimentacaoTanque;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.evolution.repository.adm.filter.MovimentacaoTanqueFilter;
import com.evolution.repository.adm.projection.MovimentacaoTanqueResumo;


public interface MovimentacaoTanqueRepositoryQuery {

	public Page<MovimentacaoTanqueResumo> resumir(MovimentacaoTanqueFilter movimentacaoTanqueFilter, Pageable pageable);

}
