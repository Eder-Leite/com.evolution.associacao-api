package com.evolution.repository.faturamento.ordemServico;

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

import com.evolution.model.cadastro.Cadastro_;
import com.evolution.model.cadastro.Veiculo_;
import com.evolution.model.faturamento.OrdemServico;
import com.evolution.model.faturamento.OrdemServico_;
import com.evolution.model.seguranca.Usuario_;
import com.evolution.repository.faturamento.filter.OrdemServicoFilter;
import com.evolution.repository.faturamento.projection.OrdemServicoResumo;


public class OrdemServicoRepositoryImpl implements OrdemServicoRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public Page<OrdemServicoResumo> resumir(OrdemServicoFilter ordemServicoFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<OrdemServicoResumo> criteria = builder.createQuery(OrdemServicoResumo.class);
		Root<OrdemServico> root = criteria.from(OrdemServico.class);

		criteria.select(builder.construct(OrdemServicoResumo.class, 
				root.get(OrdemServico_.id),
				root.get(OrdemServico_.veiculo).get(Veiculo_.id),
				root.get(OrdemServico_.veiculo).get(Veiculo_.descricao),
				root.get(OrdemServico_.usuario).get(Usuario_.id),
				root.get(OrdemServico_.usuario).get(Usuario_.nome),
				root.get(OrdemServico_.cadastro).get(Cadastro_.id),
				root.get(OrdemServico_.cadastro).get(Cadastro_.nomeRazaoSocial),
				root.get(OrdemServico_.situacao),
				root.get(OrdemServico_.placa),	
				root.get(OrdemServico_.data),
				root.get(OrdemServico_.valorDesconto),
				root.get(OrdemServico_.valorAcrescimo),
				root.get(OrdemServico_.valorProduto),
				root.get(OrdemServico_.valorTotal),
				root.get(OrdemServico_.observacao),
				root.get(OrdemServico_.cortesia)
				));

		@SuppressWarnings({ "rawtypes", "unchecked" })
		List<Order> orderList = new ArrayList();
		orderList.add(builder.asc(root.get(OrdemServico_.data)));
		orderList.add(builder.asc(root.get(OrdemServico_.cadastro).get(Cadastro_.nomeRazaoSocial)));
		criteria.orderBy(orderList);

		Predicate[] predicates = criarRestricoesResumo(ordemServicoFilter, builder, root);
		criteria.where(predicates);

		TypedQuery<OrdemServicoResumo> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query, pageable);

		return new PageImpl<>(query.getResultList(), pageable, notaResumo(ordemServicoFilter));
	}

	private Predicate[] criarRestricoesResumo(OrdemServicoFilter ordemServicoFilter, CriteriaBuilder builder,
			Root<OrdemServico> root) {
		List<Predicate> predicates = new ArrayList<>();

		if (ordemServicoFilter.getId() != null) {
			predicates.add(builder.equal(root.get(OrdemServico_.id), ordemServicoFilter.getId()));
		}

		if (ordemServicoFilter.getVeiculo() != null) {
			predicates.add(builder.equal(root.get(OrdemServico_.veiculo).get(Veiculo_.id),
					ordemServicoFilter.getVeiculo()));
		}
		
		if (ordemServicoFilter.getUsuario() != null) {
			predicates.add(builder.equal(root.get(OrdemServico_.usuario).get(Usuario_.id),
					ordemServicoFilter.getUsuario()));
		}

		if (ordemServicoFilter.getCadastro() != null) {
			predicates.add(builder.equal(root.get(OrdemServico_.cadastro).get(Cadastro_.id),
					ordemServicoFilter.getCadastro()));
		}

		if (ordemServicoFilter.getSituacao() != null) {
			predicates.add(builder.equal(root.get(OrdemServico_.situacao),
					ordemServicoFilter.getSituacao()));
		}
		
		if (ordemServicoFilter.getPlaca() != null) {
			predicates.add(builder.equal(root.get(OrdemServico_.placa),
					ordemServicoFilter.getPlaca()));
		}

		if (!StringUtils.isEmpty(ordemServicoFilter.getNomeCliente())) {
			predicates.add(builder.like(builder.lower(root.get(OrdemServico_.cadastro).get(Cadastro_.nomeRazaoSocial)),
					"%" + ordemServicoFilter.getNomeCliente().toLowerCase() + "%"));
		}

		if (ordemServicoFilter.getDataDe() != null) {
			predicates.add(builder.greaterThanOrEqualTo(
					builder.function("trunc", Date.class, root.get(OrdemServico_.data)),
					ordemServicoFilter.getDataDe()));
		}

		if (ordemServicoFilter.getDataAte() != null) {
			predicates.add(builder.lessThanOrEqualTo(
					builder.function("trunc", Date.class, root.get(OrdemServico_.data)),
					ordemServicoFilter.getDataAte()));
		}
		
		if (ordemServicoFilter.getCortesia() != null) {
			predicates.add(builder.equal(root.get(OrdemServico_.cortesia),
					ordemServicoFilter.getCortesia()));
		}

		return predicates.toArray(new Predicate[predicates.size()]);
	}

	private Long notaResumo(OrdemServicoFilter ordemServicoFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<OrdemServico> root = criteria.from(OrdemServico.class);

		Predicate[] predicates = criarRestricoesResumo(ordemServicoFilter, builder, root);
		criteria.where(predicates);

		criteria.select(builder.count(root));
		return manager.createQuery(criteria).getSingleResult();
	}

	private void adicionarRestricoesDePaginacao(TypedQuery<OrdemServicoResumo> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;

		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);

	}
}
