package com.evolution.repository.cadastro;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.evolution.model.cadastro.TipoDesconto;
import com.evolution.model.seguranca.Departamento;
import com.evolution.repository.cadastro.tipoDesconto.TipoDescontoRepositoryQuery;

@Transactional
public interface TipoDescontoRepository extends JpaRepository<TipoDesconto, Long>, TipoDescontoRepositoryQuery {

	public List<TipoDesconto> findByDepartamentoOrderByDescricaoAsc(Departamento departamento);
}
