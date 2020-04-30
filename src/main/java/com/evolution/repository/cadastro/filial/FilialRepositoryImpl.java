package com.evolution.repository.cadastro.filial;

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
import com.evolution.model.cadastro.Filial;
import com.evolution.model.cadastro.Filial_;
import com.evolution.repository.cadastro.filter.FilialFilter;
import com.evolution.repository.cadastro.projection.FilialResumo;

public class FilialRepositoryImpl implements FilialRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public Page<FilialResumo> resumir(FilialFilter filialFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<FilialResumo> criteria = builder.createQuery(FilialResumo.class);
		Root<Filial> root = criteria.from(Filial.class);

		criteria.select(builder.construct(FilialResumo.class, 
				root.get(Filial_.id),
				root.get(Filial_.empresa).get(Empresa_.id), 
				root.get(Filial_.empresa).get(Empresa_.nomeEmpresa),
				root.get(Filial_.nome),
				root.get(Filial_.codigo), 
				root.get(Filial_.cidade)
				));

		@SuppressWarnings({ "rawtypes", "unchecked" })
		List<Order> orderList = new ArrayList();
		orderList.add(builder.asc(root.get(Filial_.empresa).get(Empresa_.id)));
		orderList.add(builder.asc(root.get(Filial_.codigo)));
		criteria.orderBy(orderList);

		Predicate[] predicates = criarRestricoesResumo(filialFilter, builder, root);
		criteria.where(predicates);

		TypedQuery<FilialResumo> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query, pageable);

		return new PageImpl<>(query.getResultList(), pageable, eventoResumo(filialFilter));
	}

	private Predicate[] criarRestricoesResumo(FilialFilter filialFilter, CriteriaBuilder builder, Root<Filial> root) {
		List<Predicate> predicates = new ArrayList<>();

		if (filialFilter.getId() != null) {
			predicates.add(builder.equal(root.get(Filial_.id), filialFilter.getId()));
		}

		if (filialFilter.getEmpresa() != null) {
			predicates.add(builder.equal(root.get(Filial_.empresa).get(Empresa_.id), filialFilter.getEmpresa()));
		}


		if (!StringUtils.isEmpty(filialFilter.getNomeFilial())) {
			predicates.add(builder.like(builder.lower(root.get(Filial_.nome)),
					"%" + filialFilter.getNomeFilial().toLowerCase() + "%"));
		}

		return predicates.toArray(new Predicate[predicates.size()]);
	}

	private Long eventoResumo(FilialFilter filialFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Filial> root = criteria.from(Filial.class);

		Predicate[] predicates = criarRestricoesResumo(filialFilter, builder, root);
		criteria.where(predicates);

		criteria.select(builder.count(root));
		return manager.createQuery(criteria).getSingleResult();
	}

	private void adicionarRestricoesDePaginacao(TypedQuery<FilialResumo> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;

		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);

	}

}
