package com.evolution.repository.cadastro.projection;

import com.evolution.model.cadastro.enumerador.Check;

public class TipoDescontoResumo {

	private Long id;
	private Long departamento;
	private String nomeDepartamento;
	private String descricao;
	private Check visivel;

	public TipoDescontoResumo() {
		super();
	}

	public TipoDescontoResumo(Long id, Long departamento, String nomeDepartamento, String descricao, Check visivel) {
		super();
		this.id = id;
		this.departamento = departamento;
		this.nomeDepartamento = nomeDepartamento;
		this.descricao = descricao;
		this.visivel = visivel;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Long departamento) {
		this.departamento = departamento;
	}

	public String getNomeDepartamento() {
		return nomeDepartamento;
	}

	public void setNomeDepartamento(String nomeDepartamento) {
		this.nomeDepartamento = nomeDepartamento;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Check getVisivel() {
		return visivel;
	}

	public void setVisivel(Check visivel) {
		this.visivel = visivel;
	}

	@Override
	public String toString() {
		return "TipoDescontoResumo [id=" + id + ", departamento=" + departamento + ", nomeDepartamento="
				+ nomeDepartamento + ", descricao=" + descricao + ", visivel=" + visivel + "]";
	}

}
