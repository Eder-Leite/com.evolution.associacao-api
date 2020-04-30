package com.evolution.repository.cadastro.filter;

import com.evolution.model.cadastro.enumerador.SituacaoPeriodo;

public class PeriodoFilter {

	private Long id;
	private String descricao;
	private SituacaoPeriodo situacaoPeriodo;

	public PeriodoFilter() {
		super();
	}

	public PeriodoFilter(Long id, String descricao, SituacaoPeriodo situacaoPeriodo) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.situacaoPeriodo = situacaoPeriodo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public SituacaoPeriodo getSituacaoPeriodo() {
		return situacaoPeriodo;
	}

	public void setSituacaoPeriodo(SituacaoPeriodo situacaoPeriodo) {
		this.situacaoPeriodo = situacaoPeriodo;
	}

	@Override
	public String toString() {
		return "PeriodoFilter [id=" + id + ", descricao=" + descricao + ", situacaoPeriodo=" + situacaoPeriodo + "]";
	}

}
