package com.evolution.repository.adm.filter;

public class MedidaTanqueFilter {

	private Long id;
	private Long tanque;
	private String descricaoTanque;
	private Long numeroRegua;

	public MedidaTanqueFilter() {
	}

	public MedidaTanqueFilter(Long id, Long tanque, String descricaoTanque, Long numeroRegua) {
		this.id = id;
		this.tanque = tanque;
		this.descricaoTanque = descricaoTanque;
		this.numeroRegua = numeroRegua;
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

	@Override
	public String toString() {
		return "MedidaTanqueResumo [id=" + id + ", tanque=" + tanque + ", descricaoTanque=" + descricaoTanque
				+ ", numeroRegua=" + numeroRegua + "]";
	}

}
