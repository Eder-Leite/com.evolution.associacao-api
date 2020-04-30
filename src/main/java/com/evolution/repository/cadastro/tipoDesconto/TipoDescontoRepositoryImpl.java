package com.evolution.repository.cadastro.tipoDesconto;

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

import com.evolution.model.cadastro.TipoDesconto;
import com.evolution.model.cadastro.TipoDesconto_;
import com.evolution.model.seguranca.Departamento_;
import com.evolution.repository.cadastro.filter.TipoDescontoFilter;
import com.evolution.repository.cadastro.projection.TipoDescontoResumo;

public class TipoDescontoRepositoryImpl implements TipoDescontoRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public Page<TipoDescontoResumo> resumir(TipoDescontoFilter tipoDescontoFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<TipoDescontoResumo> criteria = builder.createQuery(TipoDescontoResumo.class);
		Root<TipoDesconto> root = criteria.from(TipoDesconto.class);

		criteria.select(builder.construct(TipoDescontoResumo.class, 
				root.get(TipoDesconto_.id),
				root.get(TipoDesconto_.departamento).get(Departamento_.id), 
				root.get(TipoDesconto_.departamento).get(Departamento_.nome), 
				root.get(TipoDesconto_.descricao),
				root.get(TipoDesconto_.visivel)));

		@SuppressWarnings({ "rawtypes", "unchecked" })
		List<Order> orderList = new ArrayList();
		orderList.add(builder.asc(root.get(TipoDesconto_.descricao)));
		criteria.orderBy(orderList);

		Predicate[] predicates = criarRestricoesResumo(tipoDescontoFilter, builder, root);
		criteria.where(predicates);

		TypedQuery<TipoDescontoResumo> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query, pageable);

		return new PageImpl<>(query.getResultList(), pageable, eventoResumo(tipoDescontoFilter));
	}

	private Long eventoResumo(TipoDescontoFilter tipoDescontoFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<TipoDesconto> root = criteria.from(TipoDesconto.class);

		Predicate[] predicates = criarRestricoesResumo(tipoDescontoFilter, builder, root);
		criteria.where(predicates);

		criteria.select(builder.count(root));
		return manager.createQuery(criteria).getSingleResult();
	}

	private void adicionarRestricoesDePaginacao(TypedQuery<TipoDescontoResumo> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;

		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);
	}

	private Predicate[] criarRestricoesResumo(TipoDescontoFilter tipoDescontoFilter, CriteriaBuilder builder,
			Root<TipoDesconto> root) {
		List<Predicate> predicates = new ArrayList<>();

		if (tipoDescontoFilter.getId() != null) {
			predicates.add(builder.equal(root.get(TipoDesconto_.id), tipoDescontoFilter.getId()));
		}

		if (tipoDescontoFilter.getDepartamento() != null) {
			predicates.add(builder.equal(root.get(TipoDesconto_.departamento).get(Departamento_.id),
					tipoDescontoFilter.getDepartamento()));
		}

		if (!StringUtils.isEmpty(tipoDescontoFilter.getDescricao())) {
			predicates.add(builder.like(builder.lower(root.get(TipoDesconto_.descricao)),
					"%" + tipoDescontoFilter.getDescricao().toLowerCase() + "%"));
		}

		return predicates.toArray(new Predicate[predicates.size()]);
	}

}
