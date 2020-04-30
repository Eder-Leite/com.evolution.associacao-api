package com.evolution.repository.adm.filter;

import com.evolution.model.adm.enumerador.Status;

public class CategoriaFilter {

	private Long id;
	private String descricao;
	private Status status;

	public CategoriaFilter() {

	}

	public CategoriaFilter(Long id, String descricao, Status status) {
		this.id = id;
		this.descricao = descricao;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "CategoriaFilter [id=" + id + ", descricao=" + descricao + ", status=" + status + "]";
	}

}
