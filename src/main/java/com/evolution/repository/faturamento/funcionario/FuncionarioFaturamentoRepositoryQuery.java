package com.evolution.repository.faturamento.funcionario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.evolution.repository.faturamento.filter.FuncionarioFilter;
import com.evolution.repository.faturamento.projection.FuncionarioResumo;


public interface FuncionarioFaturamentoRepositoryQuery {

	public Page<FuncionarioResumo> resumir(FuncionarioFilter funcionarioFilter, Pageable pageable);
}
