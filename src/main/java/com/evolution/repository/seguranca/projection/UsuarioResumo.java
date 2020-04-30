package com.evolution.repository.seguranca.projection;

import com.evolution.model.seguranca.enumerador.Status;

public class UsuarioResumo {

	private Long id;
	private Long departamento;
	private String nomeDepartamento;
	private String nome;
	private String email;
	private Status status;

	public UsuarioResumo() {
	}

	public UsuarioResumo(Long id, Long departamento, String nomeDepartamento, String nome, String email,
			Status status) {
		super();
		this.id = id;
		this.departamento = departamento;
		this.nomeDepartamento = nomeDepartamento;
		this.nome = nome;
		this.email = email;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Long departamento) {
		this.departamento = departamento;
	}

	public String getNomeDepartamento() {
		return nomeDepartamento;
	}

	public void setNomeDepartamento(String nomeDepartamento) {
		this.nomeDepartamento = nomeDepartamento;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "UsuarioResumo [id=" + id + ", departamento=" + departamento + ", nomeDepartamento=" + nomeDepartamento
				+ ", nome=" + nome + ", email=" + email + ", status=" + status + "]";
	}

}
