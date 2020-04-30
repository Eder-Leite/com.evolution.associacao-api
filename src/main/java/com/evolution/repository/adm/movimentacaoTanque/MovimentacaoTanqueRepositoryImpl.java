package com.evolution.repository.adm.movimentacaoTanque;

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

import com.evolution.model.adm.MedidaTanque_;
import com.evolution.model.adm.MovimentacaoTanque;
import com.evolution.model.adm.MovimentacaoTanque_;
import com.evolution.model.adm.Tanque_;
import com.evolution.repository.adm.filter.MovimentacaoTanqueFilter;
import com.evolution.repository.adm.projection.MovimentacaoTanqueResumo;

public class MovimentacaoTanqueRepositoryImpl implements MovimentacaoTanqueRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public Page<MovimentacaoTanqueResumo> resumir(MovimentacaoTanqueFilter movimentacaoTanqueFilter,
			Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<MovimentacaoTanqueResumo> criteria = builder.createQuery(MovimentacaoTanqueResumo.class);
		Root<MovimentacaoTanque> root = criteria.from(MovimentacaoTanque.class);

		criteria.select(builder.construct(MovimentacaoTanqueResumo.class, 
				root.get(MovimentacaoTanque_.id),
				root.get(MovimentacaoTanque_.tanque).get(Tanque_.id),
				root.get(MovimentacaoTanque_.tanque).get(Tanque_.descricao),
				root.get(MovimentacaoTanque_.medidaTanque).get(MedidaTanque_.id),
				root.get(MovimentacaoTanque_.medidaTanque).get(MedidaTanque_.numeroRegua),
				root.get(MovimentacaoTanque_.medidaTanque).get(MedidaTanque_.quantidade),
				root.get(MovimentacaoTanque_.afericao), 
				root.get(MovimentacaoTanque_.estoque),
				root.get(MovimentacaoTanque_.pendencia), 
				root.get(MovimentacaoTanque_.saldo),
				root.get(MovimentacaoTanque_.data)));

		@SuppressWarnings({ "rawtypes", "unchecked" })
		List<Order> orderList = new ArrayList();
		orderList.add(builder.asc(root.get(MovimentacaoTanque_.tanque)));
		orderList.add(builder.asc(root.get(MovimentacaoTanque_.data)));
		criteria.orderBy(orderList);

		Predicate[] predicates = criarRestricoesResumo(movimentacaoTanqueFilter, builder, root);
		criteria.where(predicates);

		TypedQuery<MovimentacaoTanqueResumo> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query, pageable);

		return new PageImpl<>(query.getResultList(), pageable, eventoResumo(movimentacaoTanqueFilter));
	}

	private Predicate[] criarRestricoesResumo(MovimentacaoTanqueFilter movimentacaoTanqueFilter,
			CriteriaBuilder builder, Root<MovimentacaoTanque> root) {
		List<Predicate> predicates = new ArrayList<>();

		if (movimentacaoTanqueFilter.getId() != null) {
			predicates.add(builder.equal(root.get(MovimentacaoTanque_.id), movimentacaoTanqueFilter.getId()));
		}

		if (movimentacaoTanqueFilter.getTanque() != null) {
			predicates.add(builder.equal(root.get(MovimentacaoTanque_.tanque).get(Tanque_.id),
					movimentacaoTanqueFilter.getTanque()));
		}

		if (movimentacaoTanqueFilter.getDataDe() != null) {
			predicates.add(builder.greaterThanOrEqualTo(
					builder.function("trunc", Date.class, root.get(MovimentacaoTanque_.data)),
					movimentacaoTanqueFilter.getDataDe()));
		}

		if (movimentacaoTanqueFilter.getDataAte() != null) {
			predicates.add(
					builder.lessThanOrEqualTo(builder.function("trunc", Date.class, root.get(MovimentacaoTanque_.data)),
							movimentacaoTanqueFilter.getDataAte()));
		}

		return predicates.toArray(new Predicate[predicates.size()]);
	}

	private Long eventoResumo(MovimentacaoTanqueFilter movimentacaoTanqueFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<MovimentacaoTanque> root = criteria.from(MovimentacaoTanque.class);

		Predicate[] predicates = criarRestricoesResumo(movimentacaoTanqueFilter, builder, root);
		criteria.where(predicates);

		criteria.select(builder.count(root));
		return manager.createQuery(criteria).getSingleResult();
	}

	private void adicionarRestricoesDePaginacao(TypedQuery<MovimentacaoTanqueResumo> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;

		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);

	}

}
