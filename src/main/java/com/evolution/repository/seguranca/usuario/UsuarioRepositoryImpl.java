package com.evolution.repository.seguranca.usuario;

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

import com.evolution.model.seguranca.Departamento_;
import com.evolution.model.seguranca.Usuario;
import com.evolution.model.seguranca.Usuario_;
import com.evolution.repository.seguranca.projection.UsuarioResumo;

public class UsuarioRepositoryImpl implements UsuarioRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public Page<UsuarioResumo> resumir(UsuarioResumo usuarioResumo, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<UsuarioResumo> criteria = builder.createQuery(UsuarioResumo.class);
		Root<Usuario> root = criteria.from(Usuario.class);

		criteria.select(builder.construct(UsuarioResumo.class, 
				root.get(Usuario_.id),
				root.get(Usuario_.departamento).get(Departamento_.id),
				root.get(Usuario_.departamento).get(Departamento_.nome), 
				root.get(Usuario_.nome),
				root.get(Usuario_.email), 
				root.get(Usuario_.status)));

		@SuppressWarnings({ "rawtypes", "unchecked" })
		List<Order> orderList = new ArrayList();
		orderList.add(builder.asc(root.get(Usuario_.nome)));
		criteria.orderBy(orderList);

		Predicate[] predicates = criarRestricoesResumo(usuarioResumo, builder, root);
		criteria.where(predicates);

		TypedQuery<UsuarioResumo> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query, pageable);

		return new PageImpl<>(query.getResultList(), pageable, UsuarioResumo(usuarioResumo));
	}

	private Predicate[] criarRestricoesResumo(UsuarioResumo usuarioResumo, CriteriaBuilder builder,
			Root<Usuario> root) {
		List<Predicate> predicates = new ArrayList<>();

		if (usuarioResumo.getId() != null) {
			predicates.add(builder.equal(root.get(Usuario_.id), usuarioResumo.getId()));
		}

		if (usuarioResumo.getDepartamento() != null) {
			predicates.add(builder.equal(root.get(Usuario_.departamento).get(Departamento_.id),
					usuarioResumo.getDepartamento()));
		}

		if (usuarioResumo.getStatus() != null) {
			predicates.add(builder.equal(root.get(Usuario_.status), usuarioResumo.getStatus()));
		}

		if (!StringUtils.isEmpty(usuarioResumo.getNome())) {
			predicates.add(builder.like(builder.lower(root.get(Usuario_.nome)),
					"%" + usuarioResumo.getNome().toLowerCase() + "%"));
		}

		return predicates.toArray(new Predicate[predicates.size()]);
	}

	private Long UsuarioResumo(UsuarioResumo usuarioResumo) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Usuario> root = criteria.from(Usuario.class);

		Predicate[] predicates = criarRestricoesResumo(usuarioResumo, builder, root);
		criteria.where(predicates);

		criteria.select(builder.count(root));
		return manager.createQuery(criteria).getSingleResult();
	}

	private void adicionarRestricoesDePaginacao(TypedQuery<UsuarioResumo> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;

		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);

	}
}
