package com.evolution.repository.cadastro.projection;

public class EventoResumo {

	private Long id;
	private Long empresa;
	private String nomeEmpresa;
	private String codigo;
	private String descricao;

	public EventoResumo() {
		super();
	}

	public EventoResumo(Long id, Long empresa, String nomeEmpresa, String codigo, String descricao) {
		super();
		this.id = id;
		this.empresa = empresa;
		this.nomeEmpresa = nomeEmpresa;
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

	public String getNomeEmpresa() {
		return nomeEmpresa;
	}

	public void setNomeEmpresa(String nomeEmpresa) {
		this.nomeEmpresa = nomeEmpresa;
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

	@Override
	public String toString() {
		return "EventoResumo [id=" + id + ", empresa=" + empresa + ", nomeEmpresa=" + nomeEmpresa + ", codigo=" + codigo
				+ ", descricao=" + descricao + "]";
	}

}
