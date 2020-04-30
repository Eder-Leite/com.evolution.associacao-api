package com.evolution.repository.adm.produto;

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
import com.evolution.model.adm.Grupo_;
import com.evolution.model.adm.Produto;
import com.evolution.model.adm.Produto_;
import com.evolution.model.adm.SubCategoria_;
import com.evolution.repository.adm.filter.ProdutoFilter;
import com.evolution.repository.adm.projection.ProdutoResumo;

public class ProdutoRepositoryImpl implements ProdutoRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public Page<ProdutoResumo> resumir(ProdutoFilter produtoFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<ProdutoResumo> criteria = builder.createQuery(ProdutoResumo.class);
		Root<Produto> root = criteria.from(Produto.class);

		criteria.select(builder.construct(ProdutoResumo.class, 
				root.get(Produto_.id),
				root.get(Produto_.subCategoria).get(SubCategoria_.categoria).get(Categoria_.id),
				root.get(Produto_.subCategoria).get(SubCategoria_.categoria).get(Categoria_.descricao),
				root.get(Produto_.subCategoria).get(SubCategoria_.id),
				root.get(Produto_.subCategoria).get(SubCategoria_.descricao), 
				root.get(Produto_.grupo).get(Grupo_.id),
				root.get(Produto_.grupo).get(Grupo_.descricao), 
				root.get(Produto_.descricao),
				root.get(Produto_.unidade), 
				root.get(Produto_.codigoBarra), 
				root.get(Produto_.valorCusto),
				root.get(Produto_.valorVenda)));

		@SuppressWarnings({ "rawtypes", "unchecked" })
		List<Order> orderList = new ArrayList();
		orderList.add(builder.asc(root.get(Produto_.descricao)));
		criteria.orderBy(orderList);

		Predicate[] predicates = criarRestricoesResumo(produtoFilter, builder, root);
		criteria.where(predicates);

		TypedQuery<ProdutoResumo> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query, pageable);

		return new PageImpl<>(query.getResultList(), pageable, eventoResumo(produtoFilter));
	}

	private Predicate[] criarRestricoesResumo(ProdutoFilter produtoFilter, CriteriaBuilder builder,
			Root<Produto> root) {
		List<Predicate> predicates = new ArrayList<>();

		if (produtoFilter.getId() != null) {
			predicates.add(builder.equal(root.get(Produto_.id), produtoFilter.getId()));
		}

		if (produtoFilter.getCategoria() != null) {
			predicates
					.add(builder.equal(root.get(Produto_.subCategoria).get(SubCategoria_.categoria).get(Categoria_.id),
							produtoFilter.getCategoria()));
		}

		if (produtoFilter.getSubCategoria() != null) {
			predicates.add(builder.equal(root.get(Produto_.subCategoria).get(SubCategoria_.id),
					produtoFilter.getSubCategoria()));
		}

		if (produtoFilter.getGrupo() != null) {
			predicates.add(builder.equal(root.get(Produto_.grupo).get(Grupo_.id), produtoFilter.getGrupo()));
		}

		if (produtoFilter.getCodigoBarra() != null) {
			predicates.add(builder.equal(root.get(Produto_.codigoBarra), produtoFilter.getCodigoBarra()));
		}

		if (!StringUtils.isEmpty(produtoFilter.getDescricao())) {
			predicates.add(builder.like(builder.lower(root.get(Produto_.descricao)),
					"%" + produtoFilter.getDescricao().toLowerCase() + "%"));
		}

		return predicates.toArray(new Predicate[predicates.size()]);
	}

	private Long eventoResumo(ProdutoFilter produtoFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Produto> root = criteria.from(Produto.class);

		Predicate[] predicates = criarRestricoesResumo(produtoFilter, builder, root);
		criteria.where(predicates);

		criteria.select(builder.count(root));
		return manager.createQuery(criteria).getSingleResult();
	}

	private void adicionarRestricoesDePaginacao(TypedQuery<ProdutoResumo> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;

		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);

	}

}
