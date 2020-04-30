package com.evolution.repository.adm.grupo;

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

import com.evolution.model.adm.Grupo;
import com.evolution.model.adm.Grupo_;
import com.evolution.repository.adm.filter.GrupoFilter;
import com.evolution.repository.adm.projection.GrupoResumo;

public class GrupoRepositoryImpl implements GrupoRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public Page<GrupoResumo> resumir(GrupoFilter grupoFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<GrupoResumo> criteria = builder.createQuery(GrupoResumo.class);
		Root<Grupo> root = criteria.from(Grupo.class);

		criteria.select(builder.construct(GrupoResumo.class, 
				root.get(Grupo_.id), 
				root.get(Grupo_.descricao),
				root.get(Grupo_.status)));

		@SuppressWarnings({ "rawtypes", "unchecked" })
		List<Order> orderList = new ArrayList();
		orderList.add(builder.asc(root.get(Grupo_.descricao)));
		criteria.orderBy(orderList);

		Predicate[] predicates = criarRestricoesResumo(grupoFilter, builder, root);
		criteria.where(predicates);

		TypedQuery<GrupoResumo> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query, pageable);

		return new PageImpl<>(query.getResultList(), pageable, eventoResumo(grupoFilter));
	}

	private Predicate[] criarRestricoesResumo(GrupoFilter grupoFilter, CriteriaBuilder builder, Root<Grupo> root) {
		List<Predicate> predicates = new ArrayList<>();

		if (grupoFilter.getId() != null) {
			predicates.add(builder.equal(root.get(Grupo_.id), grupoFilter.getId()));
		}

		if (grupoFilter.getStatus() != null) {
			predicates.add(builder.equal(root.get(Grupo_.status), grupoFilter.getStatus()));
		}

		if (!StringUtils.isEmpty(grupoFilter.getDescricao())) {
			predicates.add(builder.like(builder.lower(root.get(Grupo_.descricao)),
					"%" + grupoFilter.getDescricao().toLowerCase() + "%"));
		}

		return predicates.toArray(new Predicate[predicates.size()]);
	}

	private Long eventoResumo(GrupoFilter grupoFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Grupo> root = criteria.from(Grupo.class);

		Predicate[] predicates = criarRestricoesResumo(grupoFilter, builder, root);
		criteria.where(predicates);

		criteria.select(builder.count(root));
		return manager.createQuery(criteria).getSingleResult();
	}

	private void adicionarRestricoesDePaginacao(TypedQuery<GrupoResumo> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;

		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);

	}

}
