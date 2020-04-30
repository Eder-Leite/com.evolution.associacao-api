package com.evolution.repository.cadastro.projection;

import java.math.BigDecimal;

import com.evolution.model.cadastro.enumerador.SituacaoFuncionario;

public class FuncionarioResumo {

	private Long id;
	private Long empresa;
	private String nomeEmpresa;
	private String nomeFuncionario;
	private String email;
	private String cpf;
	private String cracha;
	private BigDecimal valorLimite;
	private SituacaoFuncionario situacaoFuncionario;

	public FuncionarioResumo() {
		super();
	}

	public FuncionarioResumo(Long id, Long empresa, String nomeEmpresa, String nomeFuncionario, String email,
			String cpf, String cracha, BigDecimal valorLimite, SituacaoFuncionario situacaoFuncionario) {
		super();
		this.id = id;
		this.empresa = empresa;
		this.nomeEmpresa = nomeEmpresa;
		this.nomeFuncionario = nomeFuncionario;
		this.email = email;
		this.cpf = cpf;
		this.cracha = cracha;
		this.valorLimite = valorLimite;
		this.situacaoFuncionario = situacaoFuncionario;
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

	public BigDecimal getValorLimite() {
		return valorLimite;
	}

	public void setValorLimite(BigDecimal valorLimite) {
		this.valorLimite = valorLimite;
	}

	public SituacaoFuncionario getSituacaoFuncionario() {
		return situacaoFuncionario;
	}

	public void setSituacaoFuncionario(SituacaoFuncionario situacaoFuncionario) {
		this.situacaoFuncionario = situacaoFuncionario;
	}

	@Override
	public String toString() {
		return "FuncionarioResumo [id=" + id + ", empresa=" + empresa + ", nomeEmpresa=" + nomeEmpresa
				+ ", nomeFuncionario=" + nomeFuncionario + ", email=" + email + ", cpf=" + cpf + ", cracha=" + cracha
				+ ", valorLimite=" + valorLimite + ", situacaoFuncionario=" + situacaoFuncionario + "]";
	}

}
