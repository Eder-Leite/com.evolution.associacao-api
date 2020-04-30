package com.evolution.repository.cadastro.filter;

public class EventoFilter {

	private Long id;
	private Long empresa;
	private String codigo;
	private String descricao;

	public EventoFilter() {
		super();
	}

	public EventoFilter(Long id, Long empresa, String codigo, String descricao) {
		super();
		this.id = id;
		this.empresa = empresa;
		this.codigo = codigo;
		this.descricao = descricao;
	}

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

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
