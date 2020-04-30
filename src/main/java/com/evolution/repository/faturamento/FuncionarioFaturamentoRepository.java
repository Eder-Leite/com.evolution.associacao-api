package com.evolution.repository.faturamento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.evolution.model.faturamento.FuncionarioFaturamento;
import com.evolution.repository.faturamento.funcionario.FuncionarioFaturamentoRepositoryQuery;

@Transactional
public interface FuncionarioFaturamentoRepository extends JpaRepository<FuncionarioFaturamento, Long>, FuncionarioFaturamentoRepositoryQuery {

}
