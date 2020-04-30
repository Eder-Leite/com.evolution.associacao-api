package com.evolution.repository.cadastro.periodo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.evolution.repository.cadastro.filter.PeriodoFilter;
import com.evolution.repository.cadastro.projection.PeriodoResumo;

public interface PeriodoRepositoryQuery {

	public Page<PeriodoResumo> resumir(PeriodoFilter periodoFilter, Pageable pageable);
}
