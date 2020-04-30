package com.evolution.repository.faturamento.filter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.time.DateUtils;

public class NotaFilter {

	private Long id;
	private Long empresa;
	private Long usuario;
	private Long departamento;
	private Long tipoDesconto;
	private Long evento;
	private Long funcionario;
	private String nomeFuncionario;
	private Long periodo;
	@Temporal(TemporalType.DATE)
	private Date dataDe;
	@Temporal(TemporalType.DATE)
	private Date dataAte;
	private List<Long> notas = new ArrayList<>();

	public NotaFilter() {
		super();
	}

	public NotaFilter(Long id, Long empresa, Long usuario, Long departamento, Long tipoDesconto, Long evento,
			Long funcionario, String nomeFuncionario, Long periodo, String dataDe, String dataAte, List<Long> notas)
			throws ParseException {
		super();

		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

		this.id = id;
		this.empresa = empresa;
		this.usuario = usuario;
		this.departamento = departamento;
		this.tipoDesconto = tipoDesconto;
		this.evento = evento;
		this.funcionario = funcionario;
		this.nomeFuncionario = nomeFuncionario;
		this.periodo = periodo;

		if (dataDe.length() > 0) {
			this.dataDe = formato.parse(dataDe);
		}

		if (dataAte.length() > 0) {
			this.dataAte = formato.parse(dataAte);
		}

		this.notas = notas;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Long empresa) {
		this.empresa = empresa;
	}

	public Long getUsuario() {
		return usuario;
	}

	public void setUsuario(Long usuario) {
		this.usuario = usuario;
	}

	public Long getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Long departamento) {
		this.departamento = departamento;
	}

	public Long getTipoDesconto() {
		return tipoDesconto;
	}

	public void setTipoDesconto(Long tipoDesconto) {
		this.tipoDesconto = tipoDesconto;
	}

	public Long getEvento() {
		return evento;
	}

	public void setEvento(Long evento) {
		this.evento = evento;
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

	public Long getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Long periodo) {
		this.periodo = periodo;
	}

	public Date getDataDe() {
		return dataDe;
	}

	public void setDataDe(String dataDe) throws ParseException {

		if (dataDe.length() > 0) {
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			this.dataDe = DateUtils.truncate(formato.parse(dataDe), Calendar.DATE);
		}
	}

	public Date getDataAte() {
		return dataAte;
	}

	public void setDataAte(String dataAte) throws ParseException {

		if (dataAte.length() > 0) {
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			this.dataAte = DateUtils.truncate(formato.parse(dataAte), Calendar.DATE);
		}
	}

	public List<Long> getNotas() {
		return notas;
	}

	public void setNotas(List<Long> notas) {
		this.notas = notas;
	}

	@Override
	public String toString() {
		return "NotaFilter [id=" + id + ", empresa=" + empresa + ", usuario=" + usuario + ", departamento="
				+ departamento + ", tipoDesconto=" + tipoDesconto + ", evento=" + evento + ", funcionario="
				+ funcionario + ", nomeFuncionario=" + nomeFuncionario + ", periodo=" + periodo + ", dataDe=" + dataDe
				+ ", dataAte=" + dataAte + ", notas=" + notas + "]";
	}
}
