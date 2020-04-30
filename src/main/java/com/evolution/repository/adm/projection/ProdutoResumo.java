package com.evolution.repository.adm.projection;

import java.math.BigDecimal;

public class ProdutoResumo {

	private Long id;
	private Long categoria;
	private String descricaoCategoria;
	private Long subCategoria;
	private String descricaoSubCategoria;
	private Long grupo;
	private String descricaoGrupo;
	private String descricao;
	private String unidade;
	private String codigoBarra;
	private BigDecimal valorCusto;
	private BigDecimal valorVenda;

	public ProdutoResumo() {
	}

	public ProdutoResumo(Long id, Long categoria, String descricaoCategoria, Long subCategoria,
			String descricaoSubCategoria, Long grupo, String descricaoGrupo, String descricao, String unidade,
			String codigoBarra, BigDecimal valorCusto, BigDecimal valorVenda) {
		this.id = id;
		this.categoria = categoria;
		this.descricaoCategoria = descricaoCategoria;
		this.subCategoria = subCategoria;
		this.descricaoSubCategoria = descricaoSubCategoria;
		this.grupo = grupo;
		this.descricaoGrupo = descricaoGrupo;
		this.descricao = descricao;
		this.unidade = unidade;
		this.codigoBarra = codigoBarra;
		this.valorCusto = valorCusto;
		this.valorVenda = valorVenda;
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

	public Long getSubCategoria() {
		return subCategoria;
	}

	public void setSubCategoria(Long subCategoria) {
		this.subCategoria = subCategoria;
	}

	public String getDescricaoSubCategoria() {
		return descricaoSubCategoria;
	}

	public void setDescricaoSubCategoria(String descricaoSubCategoria) {
		this.descricaoSubCategoria = descricaoSubCategoria;
	}

	public Long getGrupo() {
		return grupo;
	}

	public void setGrupo(Long grupo) {
		this.grupo = grupo;
	}

	public String getDescricaoGrupo() {
		return descricaoGrupo;
	}

	public void setDescricaoGrupo(String descricaoGrupo) {
		this.descricaoGrupo = descricaoGrupo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	public String getCodigoBarra() {
		return codigoBarra;
	}

	public void setCodigoBarra(String codigoBarra) {
		this.codigoBarra = codigoBarra;
	}

	public BigDecimal getValorCusto() {
		return valorCusto;
	}

	public void setValorCusto(BigDecimal valorCusto) {
		this.valorCusto = valorCusto;
	}

	public BigDecimal getValorVenda() {
		return valorVenda;
	}

	public void setValorVenda(BigDecimal valorVenda) {
		this.valorVenda = valorVenda;
	}

	@Override
	public String toString() {
		return "ProdutoResumo [id=" + id + ", categoria=" + categoria + ", descricaoCategoria=" + descricaoCategoria
				+ ", subCategoria=" + subCategoria + ", descricaoSubCategoria=" + descricaoSubCategoria + ", grupo="
				+ grupo + ", descricaoGrupo=" + descricaoGrupo + ", descricao=" + descricao + ", unidade=" + unidade
				+ ", codigoBarra=" + codigoBarra + ", valorCusto=" + valorCusto + ", valorVenda=" + valorVenda + "]";
	}

}
