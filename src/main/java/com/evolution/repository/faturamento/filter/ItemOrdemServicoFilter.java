package com.evolution.repository.faturamento.filter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.time.DateUtils;

public class ItemOrdemServicoFilter {

	private Long id;
	private Long ordemServico;
	private Long funcionario;
	private Long produto;
	private String descricaoProduto;
	@Temporal(TemporalType.DATE)
	private Date dataDe;
	@Temporal(TemporalType.DATE)
	private Date dataAte;

	public ItemOrdemServicoFilter() {
	}

	public ItemOrdemServicoFilter(Long id, Long ordemServico, Long funcionario, Long produto, String descricaoProduto,
			String dataDe, String dataAte) throws ParseException {

		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

		this.id = id;
		this.ordemServico = ordemServico;
		this.funcionario = funcionario;
		this.produto = produto;
		this.descricaoProduto = descricaoProduto;

		if (dataDe.length() > 0) {
			this.dataDe = formato.parse(dataDe);
		}

		if (dataAte.length() > 0) {
			this.dataAte = formato.parse(dataAte);
		}
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

	@Override
	public String toString() {
		return "ItemOrdemServicoFilter [id=" + id + ", ordemServico=" + ordemServico + ", funcionario=" + funcionario
				+ ", produto=" + produto + ", descricaoProduto=" + descricaoProduto + ", dataDe=" + dataDe
				+ ", dataAte=" + dataAte + "]";
	}

}
