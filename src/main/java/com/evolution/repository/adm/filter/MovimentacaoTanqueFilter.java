package com.evolution.repository.adm.filter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.time.DateUtils;

public class MovimentacaoTanqueFilter {

	private Long id;
	private Long tanque;
	@Temporal(TemporalType.DATE)
	private Date dataDe;
	@Temporal(TemporalType.DATE)
	private Date dataAte;

	public MovimentacaoTanqueFilter() {
	}

	public MovimentacaoTanqueFilter(Long id, Long tanque, String dataDe, String dataAte) throws ParseException {

		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

		this.id = id;
		this.tanque = tanque;

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

	public Long getTanque() {
		return tanque;
	}

	public void setTanque(Long tanque) {
		this.tanque = tanque;
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
		return "MovimentacaoTanqueFilter [id=" + id + ", tanque=" + tanque + ", dataDe=" + dataDe + ", dataAte="
				+ dataAte + "]";
	}

}
