package com.evolution.repository.adm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.evolution.model.adm.MovimentacaoTanque;
import com.evolution.repository.adm.movimentacaoTanque.MovimentacaoTanqueRepositoryQuery;

@Transactional
public interface MovimentacaoTanqueRepository
		extends JpaRepository<MovimentacaoTanque, Long>, MovimentacaoTanqueRepositoryQuery {

}
