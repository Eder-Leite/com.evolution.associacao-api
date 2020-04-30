package com.evolution.repository.adm.tanque;

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

import com.evolution.model.adm.Produto_;
import com.evolution.model.adm.Tanque;
import com.evolution.model.adm.Tanque_;
import com.evolution.repository.adm.filter.TanqueFilter;
import com.evolution.repository.adm.projection.TanqueResumo;


public class TanqueRepositoryImpl implements TanqueRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public Page<TanqueResumo> resumir(TanqueFilter tanqueFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<TanqueResumo> criteria = builder.createQuery(TanqueResumo.class);
		Root<Tanque> root = criteria.from(Tanque.class);

		criteria.select(builder.construct(TanqueResumo.class, 
				root.get(Tanque_.id), 
				root.get(Tanque_.produto).get(Produto_.id), 
				root.get(Tanque_.produto).get(Produto_.descricao), 
				root.get(Tanque_.numero),
				root.get(Tanque_.descricao),
				root.get(Tanque_.quantidade),
				root.get(Tanque_.status)));

		@SuppressWarnings({ "rawtypes", "unchecked" })
		List<Order> orderList = new ArrayList();
		orderList.add(builder.asc(root.get(Tanque_.numero)));
		orderList.add(builder.asc(root.get(Tanque_.descricao)));
		criteria.orderBy(orderList);

		Predicate[] predicates = criarRestricoesResumo(tanqueFilter, builder, root);
		criteria.where(predicates);

		TypedQuery<TanqueResumo> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query, pageable);

		return new PageImpl<>(query.getResultList(), pageable, eventoResumo(tanqueFilter));
	}

	private Predicate[] criarRestricoesResumo(TanqueFilter tanqueFilter, CriteriaBuilder builder, Root<Tanque> root) {
		List<Predicate> predicates = new ArrayList<>();

		if (tanqueFilter.getId() != null) {
			predicates.add(builder.equal(root.get(Tanque_.id), tanqueFilter.getId()));
		}

		if (tanqueFilter.getStatus() != null) {
			predicates.add(builder.equal(root.get(Tanque_.status), tanqueFilter.getStatus()));
		}
		
		if (tanqueFilter.getProduto() != null) {
			predicates.add(builder.equal(root.get(Tanque_.produto).get(Produto_.id), tanqueFilter.getProduto()));
		}
		
		if (tanqueFilter.getNumero() != null) {
			predicates.add(builder.equal(root.get(Tanque_.numero), tanqueFilter.getNumero()));
		}

		if (!StringUtils.isEmpty(tanqueFilter.getDescricao())) {
			predicates.add(builder.like(builder.lower(root.get(Tanque_.descricao)),
					"%" + tanqueFilter.getDescricao().toLowerCase() + "%"));
		}

		return predicates.toArray(new Predicate[predicates.size()]);
	}

	private Long eventoResumo(TanqueFilter tanqueFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Tanque> root = criteria.from(Tanque.class);

		Predicate[] predicates = criarRestricoesResumo(tanqueFilter, builder, root);
		criteria.where(predicates);

		criteria.select(builder.count(root));
		return manager.createQuery(criteria).getSingleResult();
	}

	private void adicionarRestricoesDePaginacao(TypedQuery<TanqueResumo> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;

		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);

	}

}
