package com.evolution.repository.cadastro.periodo;

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

import com.evolution.model.cadastro.Periodo;
import com.evolution.model.cadastro.Periodo_;
import com.evolution.repository.cadastro.filter.PeriodoFilter;
import com.evolution.repository.cadastro.projection.PeriodoResumo;

public class PeriodoRepositoryImpl implements PeriodoRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public Page<PeriodoResumo> resumir(PeriodoFilter periodoFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<PeriodoResumo> criteria = builder.createQuery(PeriodoResumo.class);
		Root<Periodo> root = criteria.from(Periodo.class);

		criteria.select(builder.construct(PeriodoResumo.class, 
				root.get(Periodo_.id),
				root.get(Periodo_.descricao),
				root.get(Periodo_.situacaoPeriodo)));

		@SuppressWarnings({ "rawtypes", "unchecked" })
		List<Order> orderList = new ArrayList();
		orderList.add(builder.asc(root.get(Periodo_.dataInicio)));
		criteria.orderBy(orderList);

		Predicate[] predicates = criarRestricoesResumo(periodoFilter, builder, root);
		criteria.where(predicates);

		TypedQuery<PeriodoResumo> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query, pageable);

		return new PageImpl<>(query.getResultList(), pageable, periodoResumo(periodoFilter));
	}

	private Predicate[] criarRestricoesResumo(PeriodoFilter periodoFilter, CriteriaBuilder builder,
			Root<Periodo> root) {
		List<Predicate> predicates = new ArrayList<>();

		if (periodoFilter.getId() != null) {
			predicates.add(builder.equal(root.get(Periodo_.id), periodoFilter.getId()));
		}

		if (!StringUtils.isEmpty(periodoFilter.getDescricao())) {
			predicates.add(builder.like(builder.lower(root.get(Periodo_.descricao)),
					"%" + periodoFilter.getDescricao().toLowerCase() + "%"));
		}

		if (periodoFilter.getSituacaoPeriodo() != null) {
			predicates.add(builder.equal(root.get(Periodo_.situacaoPeriodo), periodoFilter.getSituacaoPeriodo()));
		}

		return predicates.toArray(new Predicate[predicates.size()]);
	}

	private Long periodoResumo(PeriodoFilter periodoFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Periodo> root = criteria.from(Periodo.class);

		Predicate[] predicates = criarRestricoesResumo(periodoFilter, builder, root);
		criteria.where(predicates);

		criteria.select(builder.count(root));
		return manager.createQuery(criteria).getSingleResult();
	}

	private void adicionarRestricoesDePaginacao(TypedQuery<PeriodoResumo> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;

		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);

	}
}
