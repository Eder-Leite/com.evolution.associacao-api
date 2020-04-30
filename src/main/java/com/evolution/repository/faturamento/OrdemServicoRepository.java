package com.evolution.repository.faturamento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.evolution.model.faturamento.OrdemServico;
import com.evolution.repository.faturamento.ordemServico.OrdemServicoRepositoryQuery;

@Transactional
public interface OrdemServicoRepository extends JpaRepository<OrdemServico, Long>, OrdemServicoRepositoryQuery {

}
