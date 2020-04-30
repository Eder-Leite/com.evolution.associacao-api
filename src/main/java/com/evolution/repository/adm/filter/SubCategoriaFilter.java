package com.evolution.repository.adm.filter;

public class SubCategoriaFilter {

	private Long id;
	private Long categoria;
	private String descricao;
	private String status;

	public SubCategoriaFilter() {

	}

	public SubCategoriaFilter(Long id, Long categoria, String descricao, String status) {
		this.id = id;
		this.categoria = categoria;
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "SubCategoriaFilter [id=" + id + ", categoria=" + categoria + ", descricao=" + descricao + ", status="
				+ status + "]";
	}

}
