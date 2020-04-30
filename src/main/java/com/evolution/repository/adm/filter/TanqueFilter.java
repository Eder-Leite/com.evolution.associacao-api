package com.evolution.repository.adm.filter;

public class TanqueFilter {

	private Long id;
	private Long produto;
	private String numero;
	private String descricao;
	private String status;

	public TanqueFilter() {
	}

	public TanqueFilter(Long id, Long produto, String numero, String descricao, String status) {
		this.id = id;
		this.produto = produto;
		this.numero = numero;
		this.descricao = descricao;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "TanqueFilter [id=" + id + ", produto=" + produto + ", numero=" + numero + ", descricao=" + descricao
				+ ", status=" + status + "]";
	}

}
