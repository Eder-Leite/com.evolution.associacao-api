package com.evolution.repository.adm.tanque;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.evolution.repository.adm.filter.TanqueFilter;
import com.evolution.repository.adm.projection.TanqueResumo;

public interface TanqueRepositoryQuery {

	public Page<TanqueResumo> resumir(TanqueFilter tanqueFilter, Pageable pageable);

}
