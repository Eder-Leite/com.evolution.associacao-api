package com.evolution.repository.faturamento.funcionario;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.evolution.model.cadastro.FuncaoTrabalho_;
import com.evolution.model.cadastro.Setor_;
import com.evolution.model.faturamento.FuncionarioFaturamento;
import com.evolution.model.faturamento.FuncionarioFaturamento_;
import com.evolution.repository.faturamento.filter.FuncionarioFilter;
import com.evolution.repository.faturamento.projection.FuncionarioResumo;

public class FuncionarioFaturamentoRepositoryImpl implements FuncionarioFaturamentoRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public Page<FuncionarioResumo> resumir(FuncionarioFilter funcionarioFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<FuncionarioResumo> criteria = builder.createQuery(FuncionarioResumo.class);
		Root<FuncionarioFaturamento> root = criteria.from(FuncionarioFaturamento.class);

		criteria.select(builder.construct(FuncionarioResumo.class, 
				root.get(FuncionarioFaturamento_.id),
				root.get(FuncionarioFaturamento_.funcaoTrabalho).get(FuncaoTrabalho_.setor).get(Setor_.id),
				root.get(FuncionarioFaturamento_.funcaoTrabalho).get(FuncaoTrabalho_.setor).get(Setor_.descricao),
				root.get(FuncionarioFaturamento_.funcaoTrabalho).get(FuncaoTrabalho_.id),
				root.get(FuncionarioFaturamento_.funcaoTrabalho).get(FuncaoTrabalho_.descricao), 
				root.get(FuncionarioFaturamento_.nome),
				root.get(FuncionarioFaturamento_.status)));

		@SuppressWarnings({ "rawtypes", "unchecked" })
		List<Order> orderList = new ArrayList();
		orderList.add(builder.asc(root.get(FuncionarioFaturamento_.nome)));
		criteria.orderBy(orderList);

		Predicate[] predicates = criarRestricoesResumo(funcionarioFilter, builder, root);
		criteria.where(predicates);

		TypedQuery<FuncionarioResumo> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query, pageable);

		return new PageImpl<>(query.getResultList(), pageable, notaResumo(funcionarioFilter));
	}

	private Predicate[] criarRestricoesResumo(FuncionarioFilter funcionarioFilter, CriteriaBuilder builder,
			Root<FuncionarioFaturamento> root) {
		List<Predicate> predicates = new ArrayList<>();

		if (funcionarioFilter.getId() != null) {
			predicates.add(builder.equal(root.get(FuncionarioFaturamento_.id), funcionarioFilter.getId()));
		}

		if (funcionarioFilter.getSetor() != null) {
			predicates
					.add(builder.equal(root.get(FuncionarioFaturamento_.funcaoTrabalho).get(FuncaoTrabalho_.setor).get(Setor_.id),
							funcionarioFilter.getSetor()));
		}

		if (funcionarioFilter.getFuncaoTrabalho() != null) {
			predicates.add(builder.equal(root.get(FuncionarioFaturamento_.funcaoTrabalho).get(FuncaoTrabalho_.id),
					funcionarioFilter.getFuncaoTrabalho()));
		}

		if (funcionarioFilter.getStatus() != null) {
			predicates.add(builder.equal(root.get(FuncionarioFaturamento_.status), funcionarioFilter.getStatus()));
		}

		if (!StringUtils.isEmpty(funcionarioFilter.getNome())) {
			predicates.add(builder.like(builder.lower(root.get(FuncionarioFaturamento_.nome)),
					"%" + funcionarioFilter.getNome().toLowerCase() + "%"));
		}

		return predicates.toArray(new Predicate[predicates.size()]);
	}

	private Long notaResumo(FuncionarioFilter funcionarioFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<FuncionarioFaturamento> root = criteria.from(FuncionarioFaturamento.class);

		Predicate[] predicates = criarRestricoesResumo(funcionarioFilter, builder, root);
		criteria.where(predicates);

		criteria.select(builder.count(root));
		return manager.createQuery(criteria).getSingleResult();
	}

	private void adicionarRestricoesDePaginacao(TypedQuery<FuncionarioResumo> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;

		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);

	}
}
