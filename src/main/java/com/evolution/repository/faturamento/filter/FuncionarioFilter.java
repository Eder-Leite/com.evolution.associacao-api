package com.evolution.repository.faturamento.filter;

public class FuncionarioFilter {

	private Long id;
	private Long setor;
	private Long funcaoTrabalho;
	private String nome;
	private String status;

	public FuncionarioFilter() {

	}

	public FuncionarioFilter(Long id, Long setor, Long funcaoTrabalho, String nome, String status) {
		this.id = id;
		this.setor = setor;
		this.funcaoTrabalho = funcaoTrabalho;
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

	public Long getFuncaoTrabalho() {
		return funcaoTrabalho;
	}

	public void setFuncaoTrabalho(Long funcaoTrabalho) {
		this.funcaoTrabalho = funcaoTrabalho;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "FuncionarioFilter [id=" + id + ", setor=" + setor + ", funcaoTrabalho=" + funcaoTrabalho + ", nome="
				+ nome + ", status=" + status + "]";
	}

}
