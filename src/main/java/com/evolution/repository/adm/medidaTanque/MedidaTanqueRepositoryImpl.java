package com.evolution.repository.adm.medidaTanque;

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

import com.evolution.model.adm.MedidaTanque;
import com.evolution.model.adm.MedidaTanque_;
import com.evolution.model.adm.Tanque_;
import com.evolution.repository.adm.filter.MedidaTanqueFilter;
import com.evolution.repository.adm.projection.MedidaTanqueResumo;

public class MedidaTanqueRepositoryImpl implements MedidaTanqueRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public Page<MedidaTanqueResumo> resumir(MedidaTanqueFilter medidaTanqueFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<MedidaTanqueResumo> criteria = builder.createQuery(MedidaTanqueResumo.class);
		Root<MedidaTanque> root = criteria.from(MedidaTanque.class);

		criteria.select(builder.construct(MedidaTanqueResumo.class, 
				root.get(MedidaTanque_.id),
				root.get(MedidaTanque_.tanque).get(Tanque_.id), 
				root.get(MedidaTanque_.tanque).get(Tanque_.descricao),
				root.get(MedidaTanque_.numeroRegua), 
				root.get(MedidaTanque_.quantidade)));

		@SuppressWarnings({ "rawtypes", "unchecked" })
		List<Order> orderList = new ArrayList();
		orderList.add(builder.asc(root.get(MedidaTanque_.numeroRegua)));
		criteria.orderBy(orderList);

		Predicate[] predicates = criarRestricoesResumo(medidaTanqueFilter, builder, root);
		criteria.where(predicates);

		TypedQuery<MedidaTanqueResumo> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query, pageable);

		return new PageImpl<>(query.getResultList(), pageable, eventoResumo(medidaTanqueFilter));
	}

	private Predicate[] criarRestricoesResumo(MedidaTanqueFilter medidaTanqueFilter, CriteriaBuilder builder,
			Root<MedidaTanque> root) {
		List<Predicate> predicates = new ArrayList<>();

		if (medidaTanqueFilter.getId() != null) {
			predicates.add(builder.equal(root.get(MedidaTanque_.id), medidaTanqueFilter.getId()));
		}

		if (medidaTanqueFilter.getTanque() != null) {
			predicates.add(builder.equal(root.get(MedidaTanque_.tanque).get(Tanque_.id), medidaTanqueFilter.getTanque()));
		}
		
		if (medidaTanqueFilter.getNumeroRegua() != null) {
			predicates.add(builder.equal(root.get(MedidaTanque_.numeroRegua), medidaTanqueFilter.getNumeroRegua()));
		}

		return predicates.toArray(new Predicate[predicates.size()]);
	}

	private Long eventoResumo(MedidaTanqueFilter medidaTanqueFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<MedidaTanque> root = criteria.from(MedidaTanque.class);

		Predicate[] predicates = criarRestricoesResumo(medidaTanqueFilter, builder, root);
		criteria.where(predicates);

		criteria.select(builder.count(root));
		return manager.createQuery(criteria).getSingleResult();
	}

	private void adicionarRestricoesDePaginacao(TypedQuery<MedidaTanqueResumo> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;

		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);

	}

}
