package com.evolution.repository.faturamento.projection;

import com.evolution.model.faturamento.enumerador.Status;

public class FuncionarioResumo {

	private Long id;
	private Long setor;
	private String descricaoSetor;
	private Long funcaoTrabalho;
	private String descricaoFuncao;
	private String nome;
	private Status status;

	public FuncionarioResumo() {
	}

	public FuncionarioResumo(Long id, Long setor, String descricaoSetor, Long funcaoTrabalho, String descricaoFuncao,
			String nome, Status status) {
		this.id = id;
		this.setor = setor;
		this.descricaoSetor = descricaoSetor;
		this.funcaoTrabalho = funcaoTrabalho;
		this.descricaoFuncao = descricaoFuncao;
		this.nome = nome;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSetor() {
		return setor;
	}

	public void setSetor(Long setor) {
		this.setor = setor;
	}

	public String getDescricaoSetor() {
		return descricaoSetor;
	}

	public void setDescricaoSetor(String descricaoSetor) {
		this.descricaoSetor = descricaoSetor;
	}

	public Long getFuncaoTrabalho() {
		return funcaoTrabalho;
	}

	public void setFuncaoTrabalho(Long funcaoTrabalho) {
		this.funcaoTrabalho = funcaoTrabalho;
	}

	public String getDescricaoFuncao() {
		return descricaoFuncao;
	}

	public void setDescricaoFuncao(String descricaoFuncao) {
		this.descricaoFuncao = descricaoFuncao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "FuncionarioResumo [id=" + id + ", setor=" + setor + ", descricaoSetor=" + descricaoSetor
				+ ", funcaoTrabalho=" + funcaoTrabalho + ", descricaoFuncao=" + descricaoFuncao + ", nome=" + nome
				+ ", status=" + status + "]";
	}

}
