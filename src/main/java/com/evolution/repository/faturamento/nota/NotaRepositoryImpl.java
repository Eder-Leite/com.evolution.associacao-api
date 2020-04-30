package com.evolution.repository.faturamento.nota;

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

import com.evolution.model.cadastro.Empresa_;
import com.evolution.model.cadastro.Evento_;
import com.evolution.model.cadastro.Funcionario_;
import com.evolution.model.cadastro.Periodo_;
import com.evolution.model.cadastro.TipoDesconto_;
import com.evolution.model.faturamento.CodigoRetornoNota_;
import com.evolution.model.faturamento.Nota;
import com.evolution.model.faturamento.Nota_;
import com.evolution.model.seguranca.Departamento_;
import com.evolution.model.seguranca.Usuario_;
import com.evolution.repository.faturamento.filter.NotaFilter;
import com.evolution.repository.faturamento.projection.NotaResumo;

public class NotaRepositoryImpl implements NotaRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public Page<NotaResumo> resumir(NotaFilter notaFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<NotaResumo> criteria = builder.createQuery(NotaResumo.class);
		Root<Nota> root = criteria.from(Nota.class);

		criteria.select(builder.construct(NotaResumo.class, 
				root.get(Nota_.id), 
				root.get(Nota_.dataNota),
				root.get(Nota_.numeroParcela), 
				root.get(Nota_.evento).get(Evento_.codigo),
				root.get(Nota_.periodo).get(Periodo_.descricao),
				root.get(Nota_.tipoDesconto).get(TipoDesconto_.descricao), 
				root.get(Nota_.valorNota),
				root.get(Nota_.empresa).get(Empresa_.nomeFantasia),
				root.get(Nota_.departamento).get(Departamento_.nome),
				root.get(Nota_.funcionario).get(Funcionario_.nomeFuncionario),
				root.get(Nota_.usuario).get(Usuario_.nome),
				root.get(Nota_.codigoRetornoNota).get(CodigoRetornoNota_.descricao),
				root.get(Nota_.codigoRetornoNota).get(CodigoRetornoNota_.id)));

		@SuppressWarnings({ "rawtypes", "unchecked" })
		List<Order> orderList = new ArrayList();
		orderList.add(builder.asc(root.get(Nota_.dataNota)));
		orderList.add(builder.asc(root.get(Nota_.usuario).get(Usuario_.nome)));
		orderList.add(builder.asc(root.get(Nota_.id)));
		orderList.add(builder.asc(root.get(Nota_.numeroParcela)));
		criteria.orderBy(orderList);

		Predicate[] predicates = criarRestricoesResumo(notaFilter, builder, root);
		criteria.where(predicates);

		TypedQuery<NotaResumo> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query, pageable);

		return new PageImpl<>(query.getResultList(), pageable, notaResumo(notaFilter));
	}

	private Predicate[] criarRestricoesResumo(NotaFilter notaFilter, CriteriaBuilder builder, Root<Nota> root) {
		List<Predicate> predicates = new ArrayList<>();

		if (notaFilter.getId() != null) {
			predicates.add(builder.equal(root.get(Nota_.id), notaFilter.getId()));
		}

		if (notaFilter.getEmpresa() != null) {
			predicates.add(builder.equal(root.get(Nota_.empresa).get(Empresa_.id), notaFilter.getEmpresa()));
		}

		if (notaFilter.getUsuario() != null) {
			predicates.add(builder.equal(root.get(Nota_.usuario).get(Usuario_.id), notaFilter.getUsuario()));
		}

		if (notaFilter.getDepartamento() != null) {
			predicates.add(
					builder.equal(root.get(Nota_.departamento).get(Departamento_.id), notaFilter.getDepartamento()));
		}

		if (notaFilter.getTipoDesconto() != null) {
			predicates.add(
					builder.equal(root.get(Nota_.tipoDesconto).get(TipoDesconto_.id), notaFilter.getTipoDesconto()));
		}

		if (notaFilter.getEvento() != null) {
			predicates.add(builder.equal(root.get(Nota_.evento).get(Evento_.id), notaFilter.getEvento()));
		}

		if (notaFilter.getPeriodo() != null) {
			predicates.add(builder.equal(root.get(Nota_.periodo).get(Periodo_.id), notaFilter.getPeriodo()));
		}

		if (notaFilter.getFuncionario() != null) {
			predicates.add(builder.equal(root.get(Nota_.funcionario).get(Funcionario_.id), notaFilter.getPeriodo()));
		}

		if (!StringUtils.isEmpty(notaFilter.getNomeFuncionario())) {
			predicates.add(builder.like(builder.lower(root.get(Nota_.funcionario).get(Funcionario_.nomeFuncionario)),
					"%" + notaFilter.getNomeFuncionario().toLowerCase() + "%"));
		}

		if (notaFilter.getDataDe() != null) {
			predicates.add(builder.greaterThanOrEqualTo(builder.function("trunc", Date.class, root.get(Nota_.dataNota)),
					notaFilter.getDataDe()));
		}

		if (notaFilter.getDataAte() != null) {
			predicates.add(builder.lessThanOrEqualTo(builder.function("trunc", Date.class, root.get(Nota_.dataNota)),
					notaFilter.getDataAte()));
		}

		if (notaFilter.getNotas().size() > 0) {
			predicates.add(builder.and(root.get(Nota_.id).in(notaFilter.getNotas())));
		}

		return predicates.toArray(new Predicate[predicates.size()]);
	}

	private Long notaResumo(NotaFilter notaFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Nota> root = criteria.from(Nota.class);

		Predicate[] predicates = criarRestricoesResumo(notaFilter, builder, root);
		criteria.where(predicates);

		criteria.select(builder.count(root));
		return manager.createQuery(criteria).getSingleResult();
	}

	private void adicionarRestricoesDePaginacao(TypedQuery<NotaResumo> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;

		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);

	}
}
