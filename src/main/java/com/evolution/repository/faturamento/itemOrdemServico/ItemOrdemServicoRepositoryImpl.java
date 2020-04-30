package com.evolution.repository.faturamento.itemOrdemServico;

import java.sql.Date;
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
import com.evolution.model.faturamento.FuncionarioFaturamento_;
import com.evolution.model.faturamento.ItemOrdemServico;
import com.evolution.model.faturamento.ItemOrdemServico_;
import com.evolution.model.faturamento.OrdemServico_;
import com.evolution.repository.faturamento.filter.ItemOrdemServicoFilter;
import com.evolution.repository.faturamento.projection.ItemOrdemServicoResumo;

public class ItemOrdemServicoRepositoryImpl implements ItemOrdemServicoRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public Page<ItemOrdemServicoResumo> resumir(ItemOrdemServicoFilter itemOrdemServicoFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<ItemOrdemServicoResumo> criteria = builder.createQuery(ItemOrdemServicoResumo.class);
		Root<ItemOrdemServico> root = criteria.from(ItemOrdemServico.class);

		criteria.select(builder.construct(ItemOrdemServicoResumo.class, 
				root.get(ItemOrdemServico_.id),
				root.get(ItemOrdemServico_.ordemServico).get(OrdemServico_.id),
				root.get(ItemOrdemServico_.funcionario).get(FuncionarioFaturamento_.id),
				root.get(ItemOrdemServico_.funcionario).get(FuncionarioFaturamento_.nome),
				root.get(ItemOrdemServico_.produto).get(Produto_.id),
				root.get(ItemOrdemServico_.produto).get(Produto_.descricao), 
				root.get(ItemOrdemServico_.quantidade),
				root.get(ItemOrdemServico_.valorUnitario),
				root.get(ItemOrdemServico_.valorTotal)));

		@SuppressWarnings({ "rawtypes", "unchecked" })
		List<Order> orderList = new ArrayList();
		orderList.add(builder.asc(root.get(ItemOrdemServico_.produto).get(Produto_.descricao)));
		criteria.orderBy(orderList);

		Predicate[] predicates = criarRestricoesResumo(itemOrdemServicoFilter, builder, root);
		criteria.where(predicates);

		TypedQuery<ItemOrdemServicoResumo> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query, pageable);

		return new PageImpl<>(query.getResultList(), pageable, notaResumo(itemOrdemServicoFilter));
	}

	private Predicate[] criarRestricoesResumo(ItemOrdemServicoFilter itemOrdemServicoFilter, CriteriaBuilder builder,
			Root<ItemOrdemServico> root) {
		List<Predicate> predicates = new ArrayList<>();

		if (itemOrdemServicoFilter.getId() != null) {
			predicates.add(builder.equal(root.get(ItemOrdemServico_.id), itemOrdemServicoFilter.getId()));
		}

		if (itemOrdemServicoFilter.getOrdemServico() != null) {
			predicates.add(builder.equal(root.get(ItemOrdemServico_.ordemServico).get(OrdemServico_.id),
					itemOrdemServicoFilter.getOrdemServico()));
		}

		if (itemOrdemServicoFilter.getFuncionario() != null) {
			predicates.add(builder.equal(root.get(ItemOrdemServico_.funcionario).get(FuncionarioFaturamento_.id),
					itemOrdemServicoFilter.getFuncionario()));
		}

		if (itemOrdemServicoFilter.getProduto() != null) {
			predicates.add(builder.equal(root.get(ItemOrdemServico_.produto).get(Produto_.id),
					itemOrdemServicoFilter.getProduto()));
		}

		if (!StringUtils.isEmpty(itemOrdemServicoFilter.getDescricaoProduto())) {
			predicates.add(builder.like(builder.lower(root.get(ItemOrdemServico_.produto).get(Produto_.descricao)),
					"%" + itemOrdemServicoFilter.getDescricaoProduto().toLowerCase() + "%"));
		}

		if (itemOrdemServicoFilter.getDataDe() != null) {
			predicates.add(builder.greaterThanOrEqualTo(
					builder.function("trunc", Date.class,
							root.get(ItemOrdemServico_.ordemServico).get(OrdemServico_.data)),
					itemOrdemServicoFilter.getDataDe()));
		}

		if (itemOrdemServicoFilter.getDataAte() != null) {
			predicates.add(builder.lessThanOrEqualTo(
					builder.function("trunc", Date.class,
							root.get(ItemOrdemServico_.ordemServico).get(OrdemServico_.data)),
					itemOrdemServicoFilter.getDataAte()));
		}

		return predicates.toArray(new Predicate[predicates.size()]);
	}

	private Long notaResumo(ItemOrdemServicoFilter itemOrdemServicoFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<ItemOrdemServico> root = criteria.from(ItemOrdemServico.class);

		Predicate[] predicates = criarRestricoesResumo(itemOrdemServicoFilter, builder, root);
		criteria.where(predicates);

		criteria.select(builder.count(root));
		return manager.createQuery(criteria).getSingleResult();
	}

	private void adicionarRestricoesDePaginacao(TypedQuery<ItemOrdemServicoResumo> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;

		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);

	}
}
