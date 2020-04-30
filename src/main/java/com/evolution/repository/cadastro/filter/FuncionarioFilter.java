package com.evolution.repository.cadastro.filter;

import com.evolution.model.cadastro.enumerador.SituacaoFuncionario;

public class FuncionarioFilter {

	private Long id;
	private Long empresa;
	private String nomeEmpresa;
	private String nomeFuncionario;
	private String email;
	private String cpf;
	private String cracha;
	private SituacaoFuncionario situacaoFuncionario;

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

	public String getNomeFuncionario() {
		return nomeFuncionario;
	}

	public void setNomeFuncionario(String nomeFuncionario) {
		this.nomeFuncionario = nomeFuncionario;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getCracha() {
		return cracha;
	}

	public void setCracha(String cracha) {
		this.cracha = cracha;
	}

	public SituacaoFuncionario getSituacaoFuncionario() {
		return situacaoFuncionario;
	}

	public void setSituacaoFuncionario(SituacaoFuncionario situacaoFuncionario) {
		this.situacaoFuncionario = situacaoFuncionario;
	}

	@Override
	public String toString() {
		return "FuncionarioFilter [id=" + id + ", empresa=" + empresa + ", nomeEmpresa=" + nomeEmpresa
				+ ", nomeFuncionario=" + nomeFuncionario + ", email=" + email + ", cpf=" + cpf + ", cracha=" + cracha
				+ ", situacaoFuncionario=" + situacaoFuncionario + "]";
	}

}
