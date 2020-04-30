package com.evolution.repository.cadastro.evento;

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

import com.evolution.model.cadastro.Empresa_;
import com.evolution.model.cadastro.Evento;
import com.evolution.model.cadastro.Evento_;
import com.evolution.repository.cadastro.filter.EventoFilter;
import com.evolution.repository.cadastro.projection.EventoResumo;

public class EventoRepositoryImpl implements EventoRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public Page<EventoResumo> resumir(EventoFilter eventoFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<EventoResumo> criteria = builder.createQuery(EventoResumo.class);
		Root<Evento> root = criteria.from(Evento.class);

		criteria.select(builder.construct(EventoResumo.class, 
				root.get(Evento_.id),
				root.get(Evento_.empresa).get(Empresa_.id), 
				root.get(Evento_.empresa).get(Empresa_.nomeEmpresa), 
				root.get(Evento_.codigo), 
				root.get(Evento_.descricao)));

		@SuppressWarnings({ "rawtypes", "unchecked" })
		List<Order> orderList = new ArrayList();
		orderList.add(builder.asc(root.get(Evento_.codigo)));
		criteria.orderBy(orderList);

		Predicate[] predicates = criarRestricoesResumo(eventoFilter, builder, root);
		criteria.where(predicates);

		TypedQuery<EventoResumo> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query, pageable);

		return new PageImpl<>(query.getResultList(), pageable, eventoResumo(eventoFilter));
	}

	private Predicate[] criarRestricoesResumo(EventoFilter eventoFilter, CriteriaBuilder builder, Root<Evento> root) {
		List<Predicate> predicates = new ArrayList<>();

		if (eventoFilter.getId() != null) {
			predicates.add(builder.equal(root.get(Evento_.id), eventoFilter.getId()));
		}

		if (eventoFilter.getEmpresa() != null) {
			predicates.add(builder.equal(root.get(Evento_.empresa).get(Empresa_.id), eventoFilter.getEmpresa()));
		}

		if (eventoFilter.getCodigo() != null) {
			predicates.add(builder.equal(root.get(Evento_.codigo), eventoFilter.getCodigo()));
		}

		if (!StringUtils.isEmpty(eventoFilter.getDescricao())) {
			predicates.add(builder.like(builder.lower(root.get(Evento_.descricao)),
					"%" + eventoFilter.getDescricao().toLowerCase() + "%"));
		}

		return predicates.toArray(new Predicate[predicates.size()]);
	}

	private Long eventoResumo(EventoFilter eventoFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Evento> root = criteria.from(Evento.class);

		Predicate[] predicates = criarRestricoesResumo(eventoFilter, builder, root);
		criteria.where(predicates);

		criteria.select(builder.count(root));
		return manager.createQuery(criteria).getSingleResult();
	}

	private void adicionarRestricoesDePaginacao(TypedQuery<EventoResumo> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;

		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);

	}

}
