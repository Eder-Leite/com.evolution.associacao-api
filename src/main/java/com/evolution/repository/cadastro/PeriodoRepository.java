package com.evolution.repository.cadastro;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.evolution.model.cadastro.Periodo;
import com.evolution.model.cadastro.enumerador.SituacaoPeriodo;
import com.evolution.repository.cadastro.periodo.PeriodoRepositoryQuery;;

@Transactional
public interface PeriodoRepository extends JpaRepository<Periodo, Long>, PeriodoRepositoryQuery {

	public List<Periodo> findBySituacaoPeriodoOrderByDataInicioAsc(SituacaoPeriodo situacaoPeriodo);

}
