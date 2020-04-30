package com.evolution.repository.cadastro.filter;

public class FilialFilter {

	private Long id;
	private Long empresa;
	private String nomeFilial;

	public FilialFilter() {
		super();
	}

	public FilialFilter(Long id, Long empresa, String nomeFilial) {
		super();
		this.id = id;
		this.empresa = empresa;
		this.nomeFilial = nomeFilial;
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

	public String getNomeFilial() {
		return nomeFilial;
	}

	public void setNomeFilial(String nomeFilial) {
		this.nomeFilial = nomeFilial;
	}

}
