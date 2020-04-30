package com.evolution.repository.cadastro.funcionario;

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
import com.evolution.model.cadastro.Funcionario;
import com.evolution.model.cadastro.Funcionario_;
import com.evolution.repository.cadastro.filter.FuncionarioFilter;
import com.evolution.repository.cadastro.projection.FuncionarioResumo;

public class FuncionarioRepositoryImpl implements FuncionarioRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public Page<FuncionarioResumo> resumir(FuncionarioFilter funcionarioFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();

		CriteriaQuery<FuncionarioResumo> criteria = builder.createQuery(FuncionarioResumo.class);
		Root<Funcionario> root = criteria.from(Funcionario.class);

		criteria.select(builder.construct(FuncionarioResumo.class, 
				root.get(Funcionario_.id),
				root.get(Funcionario_.empresa).get(Empresa_.id),
				root.get(Funcionario_.empresa).get(Empresa_.nomeEmpresa), 
				root.get(Funcionario_.nomeFuncionario),
				root.get(Funcionario_.email),
				root.get(Funcionario_.cpf), 
				root.get(Funcionario_.cracha),
				root.get(Funcionario_.valorLimite), 
				root.get(Funcionario_.situacaoFuncionario)));

		@SuppressWarnings({ "rawtypes", "unchecked" })
		List<Order> orderList = new ArrayList();
		orderList.add(builder.asc(root.get(Funcionario_.nomeFuncionario)));
		criteria.orderBy(orderList);

		Predicate[] predicates = criarRestricoesResumo(funcionarioFilter, builder, root);
		criteria.where(predicates);

		TypedQuery<FuncionarioResumo> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query, pageable);

		return new PageImpl<>(query.getResultList(), pageable, funcionarioResumo(funcionarioFilter));
	}

	private Long funcionarioResumo(FuncionarioFilter funcionarioFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Funcionario> root = criteria.from(Funcionario.class);

		Predicate[] predicates = criarRestricoesResumo(funcionarioFilter, builder, root);
		criteria.where(predicates);

		criteria.select(builder.count(root));
		return manager.createQuery(criteria).getSingleResult();
	}

	private void adicionarRestricoesDePaginacao(TypedQuery<FuncionarioResumo> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;

		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);
	}

	private Predicate[] criarRestricoesResumo(FuncionarioFilter funcionarioFilter, CriteriaBuilder builder,
			Root<Funcionario> root) {
		List<Predicate> predicates = new ArrayList<>();

		if (funcionarioFilter.getId() != null) {
			predicates.add(builder.equal(root.get(Funcionario_.id), funcionarioFilter.getId()));
		}

		if (funcionarioFilter.getCracha() != null) {
			predicates.add(builder.equal(root.get(Funcionario_.cracha), funcionarioFilter.getCracha()));
		}

		if (funcionarioFilter.getCpf() != null) {
			predicates.add(builder.equal(root.get(Funcionario_.cpf), funcionarioFilter.getCpf()));
		}

		if (funcionarioFilter.getSituacaoFuncionario() != null) {
			predicates.add(builder.equal(root.get(Funcionario_.situacaoFuncionario),
					funcionarioFilter.getSituacaoFuncionario()));
		}

		if (funcionarioFilter.getEmpresa() != null) {
			predicates.add(
					builder.equal(root.get(Funcionario_.empresa).get(Empresa_.id), funcionarioFilter.getEmpresa()));
		}

		if (!StringUtils.isEmpty(funcionarioFilter.getEmail())) {
			predicates.add(builder.like(builder.lower(root.get(Funcionario_.email)),
					"%" + funcionarioFilter.getEmail().toLowerCase() + "%"));
		}

		if (!StringUtils.isEmpty(funcionarioFilter.getNomeFuncionario())) {
			predicates.add(builder.like(builder.lower(root.get(Funcionario_.nomeFuncionario)),
					"%" + funcionarioFilter.getNomeFuncionario().toLowerCase() + "%"));
		}

		return predicates.toArray(new Predicate[predicates.size()]);
	}

}
