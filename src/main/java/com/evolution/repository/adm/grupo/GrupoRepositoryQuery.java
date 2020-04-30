package com.evolution.repository.adm.grupo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.evolution.repository.adm.filter.GrupoFilter;
import com.evolution.repository.adm.projection.GrupoResumo;

public interface GrupoRepositoryQuery {

	public Page<GrupoResumo> resumir(GrupoFilter grupoFilter, Pageable pageable);

}
