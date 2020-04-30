package com.evolution.model.receber;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ForeignKey;

import com.evolution.model.cadastro.Empresa;
import com.evolution.model.cadastro.Evento;
import com.evolution.model.cadastro.Periodo;

@Entity
@Table(name = "RECFOLHA")
@SuppressWarnings("deprecation")
public class ProcessoFolha {

	@Id
	@Column(name = "NCODIFOLHA", nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REC_SRECFOLHA")
	@SequenceGenerator(sequenceName = "REC_SRECFOLHA", allocationSize = 1, name = "REC_SRECFOLHA")
	private Long id;

	@ManyToOne
	@NotNull(message = "é obrigatório")
	@ForeignKey(name = "FK_RECFOLHA_CADEMPRE")
	@JoinColumn(name = "NCODIEMPRE", nullable = false)
	private Empresa empresa;

	@ManyToOne
	@NotNull(message = "é obrigatório")
	@ForeignKey(name = "FK_RECFOLHA_CADEVENT")
	@JoinColumn(name = "NCODIEVENT", nullable = false)
	private Evento evento;

	@ManyToOne
	@NotNull(message = "é obrigatório")
	@ForeignKey(name = "FK_RECFOLHA_CADPERIO")
	@JoinColumn(name = "NCODIPERIO", nullable = false)
	private Periodo periodo;

	@NotNull(message = "é obrigatório")
	@Column(name = "NVALOFOLHA", nullable = false, precision = 19, scale = 2)
	private BigDecimal valorProcesso = BigDecimal.ZERO;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull(message = "é obrigatório")
	@Column(name = "DDATAFOLHA", nullable = false)
	private Date dataCadastro = new Date();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public Periodo getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}

	public BigDecimal getValorProcesso() {
		return valorProcesso;
	}

	public void setValorProcesso(BigDecimal valorProcesso) {
		this.valorProcesso = valorProcesso;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
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
		ProcessoFolha other = (ProcessoFolha) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
