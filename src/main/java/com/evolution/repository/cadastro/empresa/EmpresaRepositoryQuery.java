package com.evolution.repository.cadastro.empresa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.evolution.repository.cadastro.filter.EmpresaFilter;
import com.evolution.repository.cadastro.projection.EmpresaResumo;

public interface EmpresaRepositoryQuery {

	public Page<EmpresaResumo> resumir(EmpresaFilter empresaFilter, Pageable pageable);

}
