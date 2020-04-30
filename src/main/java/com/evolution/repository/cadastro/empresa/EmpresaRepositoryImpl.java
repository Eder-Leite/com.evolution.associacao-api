package com.evolution.repository.cadastro.empresa;

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
import com.evolution.model.cadastro.Empresa;
import com.evolution.repository.cadastro.filter.EmpresaFilter;
import com.evolution.repository.cadastro.projection.EmpresaResumo;

public class EmpresaRepositoryImpl implements EmpresaRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public Page<EmpresaResumo> resumir(EmpresaFilter empresaFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<EmpresaResumo> criteria = builder.createQuery(EmpresaResumo.class);
		Root<Empresa> root = criteria.from(Empresa.class);

		criteria.select(builder.construct(EmpresaResumo.class, 
				root.get(Empresa_.id), 
				root.get(Empresa_.nomeEmpresa),
				root.get(Empresa_.cnpj), 
				root.get(Empresa_.nomeFantasia), 
				root.get(Empresa_.nomeAssociacao)));

		@SuppressWarnings({ "rawtypes", "unchecked" })
		List<Order> orderList = new ArrayList();
		orderList.add(builder.asc(root.get(Empresa_.nomeEmpresa)));
		criteria.orderBy(orderList);

		Predicate[] predicates = criarRestricoesResumo(empresaFilter, builder, root);
		criteria.where(predicates);

		TypedQuery<EmpresaResumo> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query, pageable);

		return new PageImpl<>(query.getResultList(), pageable, eventoResumo(empresaFilter));
	}

	private Predicate[] criarRestricoesResumo(EmpresaFilter empresaFilter, CriteriaBuilder builder,
			Root<Empresa> root) {
		List<Predicate> predicates = new ArrayList<>();

		if (empresaFilter.getId() != null) {
			predicates.add(builder.equal(root.get(Empresa_.id), empresaFilter.getId()));
		}

		if (empresaFilter.getCnpj() != null) {
			predicates.add(builder.equal(root.get(Empresa_.cnpj), empresaFilter.getCnpj()));
		}

		if (!StringUtils.isEmpty(empresaFilter.getRazaoSocial())) {
			predicates.add(builder.like(builder.lower(root.get(Empresa_.nomeEmpresa)),
					"%" + empresaFilter.getRazaoSocial().toLowerCase() + "%"));
		}

		return predicates.toArray(new Predicate[predicates.size()]);
	}

	private Long eventoResumo(EmpresaFilter empresaFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Empresa> root = criteria.from(Empresa.class);

		Predicate[] predicates = criarRestricoesResumo(empresaFilter, builder, root);
		criteria.where(predicates);

		criteria.select(builder.count(root));
		return manager.createQuery(criteria).getSingleResult();
	}

	private void adicionarRestricoesDePaginacao(TypedQuery<EmpresaResumo> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;

		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);

	}

}
