package com.evolution.repository.faturamento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.evolution.model.faturamento.ItemOrdemServico;
import com.evolution.repository.faturamento.itemOrdemServico.ItemOrdemServicoRepositoryQuery;

@Transactional
public interface ItemOrdemServicoRepository
		extends JpaRepository<ItemOrdemServico, Long>, ItemOrdemServicoRepositoryQuery {

}
