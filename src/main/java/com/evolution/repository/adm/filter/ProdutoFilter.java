package com.evolution.repository.adm.filter;

public class ProdutoFilter {

	private Long id;
	private Long categoria;
	private Long subCategoria;
	private Long grupo;
	private String descricao;
	private String codigoBarra;

	public ProdutoFilter() {
	}

	public ProdutoFilter(Long id, Long categoria, Long subCategoria, Long grupo, String descricao, String codigoBarra) {
		this.id = id;
		this.categoria = categoria;
		this.subCategoria = subCategoria;
		this.grupo = grupo;
		this.descricao = descricao;
		this.codigoBarra = codigoBarra;
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

	public Long getSubCategoria() {
		return subCategoria;
	}

	public void setSubCategoria(Long subCategoria) {
		this.subCategoria = subCategoria;
	}

	public Long getGrupo() {
		return grupo;
	}

	public void setGrupo(Long grupo) {
		this.grupo = grupo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getCodigoBarra() {
		return codigoBarra;
	}

	public void setCodigoBarra(String codigoBarra) {
		this.codigoBarra = codigoBarra;
	}

	@Override
	public String toString() {
		return "ProdutoFilter [id=" + id + ", categoria=" + categoria + ", subCategoria=" + subCategoria + ", grupo="
				+ grupo + ", descricao=" + descricao + ", codigoBarra=" + codigoBarra + "]";
	}

}
