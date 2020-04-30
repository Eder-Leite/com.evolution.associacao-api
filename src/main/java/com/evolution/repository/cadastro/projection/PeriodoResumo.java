package com.evolution.repository.cadastro.projection;

import com.evolution.model.cadastro.enumerador.SituacaoPeriodo;

public class PeriodoResumo {

	private Long id;
	private String descricao;
	private SituacaoPeriodo situacao;

	public PeriodoResumo() {
		super();

	}

	public PeriodoResumo(Long id, String descricao, SituacaoPeriodo situacao) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.situacao = situacao;
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

	public SituacaoPeriodo getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoPeriodo situacao) {
		this.situacao = situacao;
	}

	@Override
	public String toString() {
		return "PeriodoResumo [id=" + id + ", descricao=" + descricao + ", situacao=" + situacao + "]";
	}

}
