package com.evolution.repository.adm.projection;

import java.math.BigDecimal;

public class MedidaTanqueResumo {

	private Long id;
	private Long tanque;
	private String descricaoTanque;
	private Long numeroRegua;
	private BigDecimal quantidade;

	public MedidaTanqueResumo() {
	}

	public MedidaTanqueResumo(Long id, Long tanque, String descricaoTanque, Long numeroRegua, BigDecimal quantidade) {
		this.id = id;
		this.tanque = tanque;
		this.descricaoTanque = descricaoTanque;
		this.numeroRegua = numeroRegua;
		this.quantidade = quantidade;
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

	@Override
	public String toString() {
		return "MedidaTanqueResumo [id=" + id + ", tanque=" + tanque + ", descricaoTanque=" + descricaoTanque
				+ ", numeroRegua=" + numeroRegua + ", quantidade=" + quantidade + "]";
	}

}
