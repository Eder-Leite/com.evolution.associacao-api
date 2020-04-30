package com.evolution.repository.cadastro.funcionario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.evolution.repository.cadastro.filter.FuncionarioFilter;
import com.evolution.repository.cadastro.projection.FuncionarioResumo;

public interface FuncionarioRepositoryQuery {

	public Page<FuncionarioResumo> resumir(FuncionarioFilter funcionarioFilter, Pageable pageable);

}
