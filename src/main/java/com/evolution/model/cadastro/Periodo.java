package com.evolution.model.cadastro;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.evolution.model.cadastro.enumerador.SituacaoPeriodo;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "CADPERIO")
public class Periodo {

	@Id
	@Column(name = "NCODIPERIO", nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CAD_SCADPERIO")
	@SequenceGenerator(sequenceName = "CAD_SCADPERIO", allocationSize = 1, name = "CAD_SCADPERIO")
	private Long id;

	@NotBlank(message = "é obrigatório")
	@Column(name = "CDESCPERIO", nullable = false, length = 255)
	private String descricao;

	@NotNull(message = "é obrigatório")
	@Column(name = "NMES_PERIO", nullable = false)
	private Long mes;

	@NotNull(message = "é obrigatório")
	@Column(name = "NANO_PERIO", nullable = false)
	private Long ano;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Column(name = "DDINIPERIO", nullable = false)
	private Date dataInicio = new Date();

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@NotNull(message = "é obrigatório")
	@Column(name = "DDFIMPERIO", nullable = false)
	private Date dataFim = new Date();

	@NotNull(message = "é obrigatório")
	@Enumerated(EnumType.STRING)
	@Column(name = "CSITUPERIO", nullable = false, length = 15)
	private SituacaoPeriodo situacaoPeriodo = SituacaoPeriodo.ABERTO;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Long getMes() {
		return mes;
	}

	public void setMes(Long mes) {
		this.mes = mes;
	}

	public Long getAno() {
		return ano;
	}

	public void setAno(Long ano) {
		this.ano = ano;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public SituacaoPeriodo getSituacaoPeriodo() {
		return situacaoPeriodo;
	}

	public void setSituacaoPeriodo(SituacaoPeriodo situacaoPeriodo) {
		this.situacaoPeriodo = situacaoPeriodo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Periodo other = (Periodo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Periodo [id=" + id + ", descricao=" + descricao + ", mes=" + mes + ", ano=" + ano + ", dataInicio="
				+ dataInicio + ", dataFim=" + dataFim + ", situacaoPeriodo=" + situacaoPeriodo + "]";
	}

}
