package com.evolution.repository.adm.categoria;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.evolution.repository.adm.filter.CategoriaFilter;
import com.evolution.repository.adm.projection.CategoriaResumo;

public interface CategoriaRepositoryQuery {

	public Page<CategoriaResumo> resumir(CategoriaFilter categoriaFilter, Pageable pageable);

}
