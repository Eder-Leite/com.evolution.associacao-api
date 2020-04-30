package com.evolution.repository.adm.projection;

import com.evolution.model.adm.enumerador.Status;

public class SubCategoriaResumo {

	private Long id;
	private Long categoria;
	private String descricaoCategoria;
	private String descricao;
	private Status status;

	public SubCategoriaResumo() {
	}

	public SubCategoriaResumo(Long id, Long categoria, String descricaoCategoria, String descricao, Status status) {
		this.id = id;
		this.categoria = categoria;
		this.descricaoCategoria = descricaoCategoria;
		this.descricao = descricao;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCategoria() {
		return categoria;
	}

	public void setCategoria(Long categoria) {
		this.categoria = categoria;
	}

	public String getDescricaoCategoria() {
		return descricaoCategoria;
	}

	public void setDescricaoCategoria(String descricaoCategoria) {
		this.descricaoCategoria = descricaoCategoria;
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
		return "SubCategoriaResumo [id=" + id + ", categoria=" + categoria + ", descricaoCategoria="
				+ descricaoCategoria + ", descricao=" + descricao + ", status=" + status + "]";
	}

}
