package com.evolution.repository.faturamento.filter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.time.DateUtils;

public class OrdemServicoFilter {

	private Long id;
	private Long veiculo;
	private Long usuario;
	private Long cadastro;
	private String nomeCliente;
	private String situacao;
	private String placa;

	@Temporal(TemporalType.DATE)
	private Date dataDe;

	@Temporal(TemporalType.DATE)
	private Date dataAte;

	private String cortesia;

	public OrdemServicoFilter() {
	}

	public OrdemServicoFilter(Long id, Long veiculo, Long usuario, Long cadastro, String nomeCliente, String situacao,
			String placa, String dataDe, String dataAte, String cortesia) throws ParseException {

		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

		this.id = id;
		this.veiculo = veiculo;
		this.usuario = usuario;
		this.cadastro = cadastro;
		this.nomeCliente = nomeCliente;
		this.situacao = situacao;
		this.placa = placa;

		if (dataDe.length() > 0) {
			this.dataDe = formato.parse(dataDe);
		}

		if (dataAte.length() > 0) {
			this.dataAte = formato.parse(dataAte);
		}

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

	public Long getUsuario() {
		return usuario;
	}

	public void setUsuario(Long usuario) {
		this.usuario = usuario;
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

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
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

	public String getCortesia() {
		return cortesia;
	}

	public void setCortesia(String cortesia) {
		this.cortesia = cortesia;
	}

	@Override
	public String toString() {
		return "OrdemServicoFilter [id=" + id + ", veiculo=" + veiculo + ", usuario=" + usuario + ", cadastro="
				+ cadastro + ", nomeCliente=" + nomeCliente + ", situacao=" + situacao + ", placa=" + placa
				+ ", dataDe=" + dataDe + ", dataAte=" + dataAte + ", cortesia=" + cortesia + "]";
	}

}
