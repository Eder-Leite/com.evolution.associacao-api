package com.evolution.repository.faturamento.filter;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;

public class ManipulaNota {

	private Long empresa;
	private Long nota;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String tipo;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Long evento;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Long tipoDesconto;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Long periodo;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Long parcela;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Long funcionario;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Long usuario;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private BigDecimal valor;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String senha;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String mensagem;

	public ManipulaNota() {
		super();
	}

	public Long getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Long empresa) {
		this.empresa = empresa;
	}

	public Long getNota() {
		return nota;
	}

	public void setNota(Long nota) {
		this.nota = nota;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Long getEvento() {
		return evento;
	}

	public void setEvento(Long evento) {
		this.evento = evento;
	}

	public Long getTipoDesconto() {
		return tipoDesconto;
	}

	public void setTipoDesconto(Long tipoDesconto) {
		this.tipoDesconto = tipoDesconto;
	}

	public Long getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Long periodo) {
		this.periodo = periodo;
	}

	public Long getParcela() {
		return parcela;
	}

	public void setParcela(Long parcela) {
		this.parcela = parcela;
	}

	public Long getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Long funcionario) {
		this.funcionario = funcionario;
	}

	public Long getUsuario() {
		return usuario;
	}

	public void setUsuario(Long usuario) {
		this.usuario = usuario;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	@Override
	public String toString() {
		return "ManipulaNota [empresa=" + empresa + ", nota=" + nota + ", tipo=" + tipo + ", evento=" + evento
				+ ", tipoDesconto=" + tipoDesconto + ", periodo=" + periodo + ", parcela=" + parcela + ", funcionario="
				+ funcionario + ", usuario=" + usuario + ", valor=" + valor + ", senha=" + senha + ", mensagem="
				+ mensagem + "]";
	}

}
