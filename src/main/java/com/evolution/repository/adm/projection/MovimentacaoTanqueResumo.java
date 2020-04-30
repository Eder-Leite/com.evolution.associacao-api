package com.evolution.repository.adm.projection;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class MovimentacaoTanqueResumo {

	private Long id;
	private Long tanque;
	private String descricaoTanque;
	private Long medidaTanque;
	private Long numeroRegua;
	private BigDecimal quantidade;
	private BigDecimal afericao;
	private BigDecimal estoque;
	private BigDecimal pendencia;
	private BigDecimal saldo;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date data;

	public MovimentacaoTanqueResumo() {
	}

	public MovimentacaoTanqueResumo(Long id, Long tanque, String descricaoTanque, Long medidaTanque, Long numeroRegua,
			BigDecimal quantidade, BigDecimal afericao, BigDecimal estoque, BigDecimal pendencia, BigDecimal saldo,
			Date data) {
		this.id = id;
		this.tanque = tanque;
		this.descricaoTanque = descricaoTanque;
		this.medidaTanque = medidaTanque;
		this.numeroRegua = numeroRegua;
		this.quantidade = quantidade;
		this.afericao = afericao;
		this.estoque = estoque;
		this.pendencia = pendencia;
		this.saldo = saldo;
		this.data = data;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTanque() {
		return tanque;
	}

	public void setTanque(Long tanque) {
		this.tanque = tanque;
	}

	public String getDescricaoTanque() {
		return descricaoTanque;
	}

	public void setDescricaoTanque(String descricaoTanque) {
		this.descricaoTanque = descricaoTanque;
	}

	public Long getMedidaTanque() {
		return medidaTanque;
	}

	public void setMedidaTanque(Long medidaTanque) {
		this.medidaTanque = medidaTanque;
	}

	public Long getNumeroRegua() {
		return numeroRegua;
	}

	public void setNumeroRegua(Long numeroRegua) {
		this.numeroRegua = numeroRegua;
	}

	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getAfericao() {
		return afericao;
	}

	public void setAfericao(BigDecimal afericao) {
		this.afericao = afericao;
	}

	public BigDecimal getEstoque() {
		return estoque;
	}

	public void setEstoque(BigDecimal estoque) {
		this.estoque = estoque;
	}

	public BigDecimal getPendencia() {
		return pendencia;
	}

	public void setPendencia(BigDecimal pendencia) {
		this.pendencia = pendencia;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "MovimentacaoTanqueResumo [id=" + id + ", tanque=" + tanque + ", descricaoTanque=" + descricaoTanque
				+ ", medidaTanque=" + medidaTanque + ", numeroRegua=" + numeroRegua + ", quantidade=" + quantidade
				+ ", afericao=" + afericao + ", estoque=" + estoque + ", pendencia=" + pendencia + ", saldo=" + saldo
				+ ", data=" + data + "]";
	}

}
