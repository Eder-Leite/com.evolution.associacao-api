package com.evolution.repository.cadastro.projection;

public class EmpresaResumo {

	private Long id;
	private String razaoSocial;
	private String cnpj;
	private String nomeFantasia;
	private String nomeAssociacao;

	public EmpresaResumo() {
		super();
	}

	public EmpresaResumo(Long id, String razaoSocial, String cnpj, String nomeFantasia, String nomeAssociacao) {
		this.id = id;
		this.razaoSocial = razaoSocial;
		this.cnpj = cnpj;
		this.nomeFantasia = nomeFantasia;
		this.nomeAssociacao = nomeAssociacao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getNomeAssociacao() {
		return nomeAssociacao;
	}

	public void setNomeAssociacao(String nomeAssociacao) {
		this.nomeAssociacao = nomeAssociacao;
	}

	@Override
	public String toString() {
		return "EmpresaResumo [id=" + id + ", razaoSocial=" + razaoSocial + ", cnpj=" + cnpj + ", nomeFantasia="
				+ nomeFantasia + ", nomeAssociacao=" + nomeAssociacao + "]";
	}

}
