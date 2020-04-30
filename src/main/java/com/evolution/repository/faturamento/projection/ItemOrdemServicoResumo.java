package com.evolution.repository.faturamento.projection;

import java.math.BigDecimal;

public class ItemOrdemServicoResumo {

	private Long id;
	private Long ordemServico;
	private Long funcionario;
	private String nomeFuncionario;
	private Long produto;
	private String descricaoProduto;
	private BigDecimal quantidade;
	private BigDecimal valorUnitario;
	private BigDecimal valorTotal;

	public ItemOrdemServicoResumo() {
	}

	public ItemOrdemServicoResumo(Long id, Long ordemServico, Long funcionario, String nomeFuncionario, Long produto,
			String descricaoProduto, BigDecimal quantidade, BigDecimal valorUnitario, BigDecimal valorTotal) {
		this.id = id;
		this.ordemServico = ordemServico;
		this.funcionario = funcionario;
		this.nomeFuncionario = nomeFuncionario;
		this.produto = produto;
		this.descricaoProduto = descricaoProduto;
		this.quantidade = quantidade;
		this.valorUnitario = valorUnitario;
		this.valorTotal = valorTotal;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrdemServico() {
		return ordemServico;
	}

	public void setOrdemServico(Long ordemServico) {
		this.ordemServico = ordemServico;
	}

	public Long getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Long funcionario) {
		this.funcionario = funcionario;
	}

	public String getNomeFuncionario() {
		return nomeFuncionario;
	}

	public void setNomeFuncionario(String nomeFuncionario) {
		this.nomeFuncionario = nomeFuncionario;
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

	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	@Override
	public String toString() {
		return "ItemServicoResumo [id=" + id + ", ordemServico=" + ordemServico + ", funcionario=" + funcionario
				+ ", nomeFuncionario=" + nomeFuncionario + ", produto=" + produto + ", descricaoProduto="
				+ descricaoProduto + ", quantidade=" + quantidade + ", valorUnitario=" + valorUnitario + ", valorTotal="
				+ valorTotal + "]";
	}

}
