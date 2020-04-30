package com.evolution.repository.adm.subCategoria;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.evolution.repository.adm.filter.SubCategoriaFilter;
import com.evolution.repository.adm.projection.SubCategoriaResumo;

public interface SubCategoriaRepositoryQuery {

	public Page<SubCategoriaResumo> resumir(SubCategoriaFilter subCategoriaFilter, Pageable pageable);

}
