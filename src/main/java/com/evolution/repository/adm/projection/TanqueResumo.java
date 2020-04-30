package com.evolution.repository.adm.projection;

import java.math.BigDecimal;

import com.evolution.model.adm.enumerador.Status;

public class TanqueResumo {

	private Long id;
	private Long produto;
	private String descricaoProduto;
	private String numero;
	private String descricao;
	private BigDecimal quantidade;
	private Status status;

	public TanqueResumo() {
	}

	public TanqueResumo(Long id, Long produto, String descricaoProduto, String numero, String descricao,
			BigDecimal quantidade, Status status) {
		this.id = id;
		this.produto = produto;
		this.descricaoProduto = descricaoProduto;
		this.numero = numero;
		this.descricao = descricao;
		this.quantidade = quantidade;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProduto() {
		return produto;
	}

	public void setProduto(Long produto) {
		this.produto = produto;
	}

	public String getDescricaoProduto() {
		return descricaoProduto;
	}

	public void setDescricaoProduto(String descricaoProduto) {
		this.descricaoProduto = descricaoProduto;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "TanqueResumo [id=" + id + ", produto=" + produto + ", descricaoProduto=" + descricaoProduto
				+ ", numero=" + numero + ", descricao=" + descricao + ", quantidade=" + quantidade + ", status="
				+ status + "]";
	}

}
