package com.evolution.repository.faturamento.nota;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.evolution.repository.faturamento.filter.NotaFilter;
import com.evolution.repository.faturamento.projection.NotaResumo;

public interface NotaRepositoryQuery {

	public Page<NotaResumo> resumir(NotaFilter notaFilter, Pageable pageable);
}
