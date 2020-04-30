package com.evolution.repository.cadastro.projection;

public class FilialResumo {

	private Long id;
	private Long empresa;
	private String nomeEmpresa;
	private String nomeFilial;
	private String codigo;
	private String cidade;

	public FilialResumo() {
		super();
	}

	public FilialResumo(Long id, Long empresa, String nomeEmpresa, String nomeFilial, String codigo, String cidade) {
		super();
		this.id = id;
		this.empresa = empresa;
		this.nomeEmpresa = nomeEmpresa;
		this.nomeFilial = nomeFilial;
		this.codigo = codigo;
		this.cidade = cidade;
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

	public String getNomeFilial() {
		return nomeFilial;
	}

	public void setNomeFilial(String nomeFilial) {
		this.nomeFilial = nomeFilial;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	@Override
	public String toString() {
		return "FilialResumo [id=" + id + ", empresa=" + empresa + ", nomeEmpresa=" + nomeEmpresa + ", nomeFilial="
				+ nomeFilial + ", codigo=" + codigo + ", cidade=" + cidade + "]";
	}

}
