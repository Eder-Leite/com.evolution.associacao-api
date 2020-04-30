package com.evolution.repository.cadastro.filial;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.evolution.repository.cadastro.filter.FilialFilter;
import com.evolution.repository.cadastro.projection.FilialResumo;

public interface FilialRepositoryQuery {

	public Page<FilialResumo> resumir(FilialFilter filialFilter, Pageable pageable);

}
