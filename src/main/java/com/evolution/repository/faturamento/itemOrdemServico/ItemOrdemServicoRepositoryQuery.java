package com.evolution.repository.faturamento.itemOrdemServico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.evolution.repository.faturamento.filter.ItemOrdemServicoFilter;
import com.evolution.repository.faturamento.projection.ItemOrdemServicoResumo;

public interface ItemOrdemServicoRepositoryQuery {

	public Page<ItemOrdemServicoResumo> resumir(ItemOrdemServicoFilter itemOrdemServicoFilter, Pageable pageable);
}
