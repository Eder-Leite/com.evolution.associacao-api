package com.evolution.repository.adm.subCategoria;

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

import com.evolution.model.adm.Categoria_;
import com.evolution.model.adm.SubCategoria;
import com.evolution.model.adm.SubCategoria_;
import com.evolution.repository.adm.filter.SubCategoriaFilter;
import com.evolution.repository.adm.projection.SubCategoriaResumo;

public class SubCategoriaRepositoryImpl implements SubCategoriaRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public Page<SubCategoriaResumo> resumir(SubCategoriaFilter subCategoriaFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<SubCategoriaResumo> criteria = builder.createQuery(SubCategoriaResumo.class);
		Root<SubCategoria> root = criteria.from(SubCategoria.class);

		criteria.select(builder.construct(SubCategoriaResumo.class, 
				root.get(SubCategoria_.id),
				root.get(SubCategoria_.categoria).get(Categoria_.id),
				root.get(SubCategoria_.categoria).get(Categoria_.descricao), 
				root.get(SubCategoria_.descricao),
				root.get(SubCategoria_.status)));

		@SuppressWarnings({ "rawtypes", "unchecked" })
		List<Order> orderList = new ArrayList();
		orderList.add(builder.asc(root.get(SubCategoria_.descricao)));
		criteria.orderBy(orderList);

		Predicate[] predicates = criarRestricoesResumo(subCategoriaFilter, builder, root);
		criteria.where(predicates);

		TypedQuery<SubCategoriaResumo> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query, pageable);

		return new PageImpl<>(query.getResultList(), pageable, eventoResumo(subCategoriaFilter));
	}

	private Predicate[] criarRestricoesResumo(SubCategoriaFilter subCategoriaFilter, CriteriaBuilder builder,
			Root<SubCategoria> root) {
		List<Predicate> predicates = new ArrayList<>();

		if (subCategoriaFilter.getId() != null) {
			predicates.add(builder.equal(root.get(SubCategoria_.id), subCategoriaFilter.getId()));
		}

		if (subCategoriaFilter.getCategoria() != null) {
			predicates.add(builder.equal(root.get(SubCategoria_.categoria).get(Categoria_.id),
					subCategoriaFilter.getCategoria()));
		}

		if (subCategoriaFilter.getStatus() != null) {
			predicates.add(builder.equal(root.get(SubCategoria_.status), subCategoriaFilter.getStatus()));
		}

		if (!StringUtils.isEmpty(subCategoriaFilter.getDescricao())) {
			predicates.add(builder.like(builder.lower(root.get(SubCategoria_.descricao)),
					"%" + subCategoriaFilter.getDescricao().toLowerCase() + "%"));
		}

		return predicates.toArray(new Predicate[predicates.size()]);
	}

	private Long eventoResumo(SubCategoriaFilter subCategoriaFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<SubCategoria> root = criteria.from(SubCategoria.class);

		Predicate[] predicates = criarRestricoesResumo(subCategoriaFilter, builder, root);
		criteria.where(predicates);

		criteria.select(builder.count(root));
		return manager.createQuery(criteria).getSingleResult();
	}

	private void adicionarRestricoesDePaginacao(TypedQuery<SubCategoriaResumo> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;

		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);

	}

}
