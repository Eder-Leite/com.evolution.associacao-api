package com.evolution.repository.adm.categoria;

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

import com.evolution.model.adm.Categoria;
import com.evolution.model.adm.Categoria_;
import com.evolution.repository.adm.filter.CategoriaFilter;
import com.evolution.repository.adm.projection.CategoriaResumo;

public class CategoriaRepositoryImpl implements CategoriaRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public Page<CategoriaResumo> resumir(CategoriaFilter categoriaFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<CategoriaResumo> criteria = builder.createQuery(CategoriaResumo.class);
		Root<Categoria> root = criteria.from(Categoria.class);

		criteria.select(builder.construct(CategoriaResumo.class, 
				root.get(Categoria_.id),
				root.get(Categoria_.descricao), 
				root.get(Categoria_.status)));

		@SuppressWarnings({ "rawtypes", "unchecked" })
		List<Order> orderList = new ArrayList();
		orderList.add(builder.asc(root.get(Categoria_.descricao)));
		criteria.orderBy(orderList);

		Predicate[] predicates = criarRestricoesResumo(categoriaFilter, builder, root);
		criteria.where(predicates);

		TypedQuery<CategoriaResumo> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query, pageable);

		return new PageImpl<>(query.getResultList(), pageable, eventoResumo(categoriaFilter));
	}

	private Predicate[] criarRestricoesResumo(CategoriaFilter categoriaFilter, CriteriaBuilder builder,
			Root<Categoria> root) {
		List<Predicate> predicates = new ArrayList<>();

		if (categoriaFilter.getId() != null) {
			predicates.add(builder.equal(root.get(Categoria_.id), categoriaFilter.getId()));
		}

		if (categoriaFilter.getStatus() != null) {
			predicates.add(builder.equal(root.get(Categoria_.status), categoriaFilter.getStatus()));
		}

		if (!StringUtils.isEmpty(categoriaFilter.getDescricao())) {
			predicates.add(builder.like(builder.lower(root.get(Categoria_.descricao)),
					"%" + categoriaFilter.getDescricao().toLowerCase() + "%"));
		}

		return predicates.toArray(new Predicate[predicates.size()]);
	}

	private Long eventoResumo(CategoriaFilter categoriaFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Categoria> root = criteria.from(Categoria.class);

		Predicate[] predicates = criarRestricoesResumo(categoriaFilter, builder, root);
		criteria.where(predicates);

		criteria.select(builder.count(root));
		return manager.createQuery(criteria).getSingleResult();
	}

	private void adicionarRestricoesDePaginacao(TypedQuery<CategoriaResumo> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;

		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);

	}

}
