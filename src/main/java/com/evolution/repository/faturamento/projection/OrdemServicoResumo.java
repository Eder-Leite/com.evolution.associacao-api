package com.evolution.repository.faturamento.projection;

import java.math.BigDecimal;
import java.util.Date;

import com.evolution.model.faturamento.enumerador.Cortesia;
import com.evolution.model.faturamento.enumerador.SituacaoOrdemServico;
import com.fasterxml.jackson.annotation.JsonFormat;

public class OrdemServicoResumo {

	private Long id;
	private Long veiculo;
	private String descricaoVeiculo;
	private Long usuario;
	private String nomeUsuario;
	private Long cadastro;
	private String nomeCliente;
	private SituacaoOrdemServico situacao;
	private String placa;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
	private Date data;
	private BigDecimal valorDesconto;
	private BigDecimal valorAcrescimo;
	private BigDecimal valorProduto;
	private BigDecimal valorTotal;
	private String observacao;
	private Cortesia cortesia;

	public OrdemServicoResumo() {
	}

	public OrdemServicoResumo(Long id, Long veiculo, String descricaoVeiculo, Long usuario, String nomeUsuario,
			Long cadastro, String nomeCliente, SituacaoOrdemServico situacao, String placa, Date data,
			BigDecimal valorDesconto, BigDecimal valorAcrescimo, BigDecimal valorProduto, BigDecimal valorTotal,
			String observacao, Cortesia cortesia) {
		super();
		this.id = id;
		this.veiculo = veiculo;
		this.descricaoVeiculo = descricaoVeiculo;
		this.usuario = usuario;
		this.nomeUsuario = nomeUsuario;
		this.cadastro = cadastro;
		this.nomeCliente = nomeCliente;
		this.situacao = situacao;
		this.placa = placa;
		this.data = data;
		this.valorDesconto = valorDesconto;
		this.valorAcrescimo = valorAcrescimo;
		this.valorProduto = valorProduto;
		this.valorTotal = valorTotal;
		this.observacao = observacao;
		this.cortesia = cortesia;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(Long veiculo) {
		this.veiculo = veiculo;
	}

	public String getDescricaoVeiculo() {
		return descricaoVeiculo;
	}

	public void setDescricaoVeiculo(String descricaoVeiculo) {
		this.descricaoVeiculo = descricaoVeiculo;
	}

	public Long getUsuario() {
		return usuario;
	}

	public void setUsuario(Long usuario) {
		this.usuario = usuario;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public Long getCadastro() {
		return cadastro;
	}

	public void setCadastro(Long cadastro) {
		this.cadastro = cadastro;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public SituacaoOrdemServico getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoOrdemServico situacao) {
		this.situacao = situacao;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public BigDecimal getValorDesconto() {
		return valorDesconto;
	}

	public void setValorDesconto(BigDecimal valorDesconto) {
		this.valorDesconto = valorDesconto;
	}

	public BigDecimal getValorAcrescimo() {
		return valorAcrescimo;
	}

	public void setValorAcrescimo(BigDecimal valorAcrescimo) {
		this.valorAcrescimo = valorAcrescimo;
	}

	public BigDecimal getValorProduto() {
		return valorProduto;
	}

	public void setValorProduto(BigDecimal valorProduto) {
		this.valorProduto = valorProduto;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Cortesia getCortesia() {
		return cortesia;
	}

	public void setCortesia(Cortesia cortesia) {
		this.cortesia = cortesia;
	}

	@Override
	public String toString() {
		return "OrdemServicoResumo [id=" + id + ", veiculo=" + veiculo + ", descricaoVeiculo=" + descricaoVeiculo
				+ ", usuario=" + usuario + ", nomeUsuario=" + nomeUsuario + ", cadastro=" + cadastro + ", nomeCliente="
				+ nomeCliente + ", situacao=" + situacao + ", placa=" + placa + ", data=" + data + ", valorDesconto="
				+ valorDesconto + ", valorAcrescimo=" + valorAcrescimo + ", valorProduto=" + valorProduto
				+ ", valorTotal=" + valorTotal + ", observacao=" + observacao + ", cortesia=" + cortesia + "]";
	}

}
