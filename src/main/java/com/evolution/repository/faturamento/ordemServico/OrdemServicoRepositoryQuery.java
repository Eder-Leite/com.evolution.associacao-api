package com.evolution.repository.faturamento.ordemServico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.evolution.repository.faturamento.filter.OrdemServicoFilter;
import com.evolution.repository.faturamento.projection.OrdemServicoResumo;

public interface OrdemServicoRepositoryQuery {

	public Page<OrdemServicoResumo> resumir(OrdemServicoFilter ordemServicoFilter, Pageable pageable);
}
