package com.evolution.repository.faturamento.projection;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class NotaResumo {

	private Long id;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
	private Date data;
	private String parcela;
	private String evento;
	private String periodo;
	private String tipoDesconto;
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private BigDecimal valor;
	private String empresa;
	private String departamento;
	private String funcionario;
	private String usuario;
	private String situacao;
	private Long codigo;

	public NotaResumo() {
		super();
	}

	public NotaResumo(Long id, Date data, String parcela, String evento, String periodo, String tipoDesconto,
			BigDecimal valor, String empresa, String departamento, String funcionario, String usuario, String situacao,
			Long codigo) {
		super();
		this.id = id;
		this.data = data;
		this.parcela = parcela;
		this.evento = evento;
		this.periodo = periodo;
		this.tipoDesconto = tipoDesconto;
		this.valor = valor;
		this.empresa = empresa;
		this.departamento = departamento;
		this.funcionario = funcionario;
		this.usuario = usuario;
		this.situacao = situacao;
		this.codigo = codigo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getParcela() {
		return parcela;
	}

	public void setParcela(String parcela) {
		this.parcela = parcela;
	}

	public String getEvento() {
		return evento;
	}

	public void setEvento(String evento) {
		this.evento = evento;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public String getTipoDesconto() {
		return tipoDesconto;
	}

	public void setTipoDesconto(String tipoDesconto) {
		this.tipoDesconto = tipoDesconto;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(String funcionario) {
		this.funcionario = funcionario;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	@Override
	public String toString() {
		return "NotaResumo [id=" + id + ", data=" + data + ", parcela=" + parcela + ", evento=" + evento + ", periodo="
				+ periodo + ", tipoDesconto=" + tipoDesconto + ", valor=" + valor + ", empresa=" + empresa
				+ ", departamento=" + departamento + ", funcionario=" + funcionario + ", usuario=" + usuario
				+ ", situacao=" + situacao + ", codigo=" + codigo + "]";
	}

}
