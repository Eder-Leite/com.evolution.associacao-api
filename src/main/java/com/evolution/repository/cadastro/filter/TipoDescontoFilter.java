package com.evolution.repository.cadastro.filter;

import com.evolution.model.cadastro.enumerador.Check;

public class TipoDescontoFilter {

	private Long id;
	private Long empresa;
	private Long departamento;
	private String descricao;
	private Check visivel;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Long empresa) {
		this.empresa = empresa;
	}

	public Long getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Long departamento) {
		this.departamento = departamento;
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

}
