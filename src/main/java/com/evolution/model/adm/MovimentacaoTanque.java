package com.evolution.model.adm;

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

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "ADMMVTAN")
public class MovimentacaoTanque {

	@Id
	@Column(name = "NCODIMVTAN", nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ADM_SADMMVTAN")
	@SequenceGenerator(sequenceName = "ADM_SADMMVTAN", allocationSize = 1, name = "ADM_SADMMVTAN")
	private Long id;

	@ManyToOne
	@NotNull(message = "é obrigatório")
	@JoinColumn(name = "NCODITANQU", nullable = false)
	private Tanque tanque;

	@ManyToOne
	@NotNull(message = "é obrigatório")
	@JoinColumn(name = "NCODIMTANQ", nullable = false)
	private MedidaTanque medidaTanque;

	@NotNull(message = "é obrigatório")
	@Column(name = "NAFERMTANQ", nullable = false, precision = 19, scale = 3)
	private BigDecimal afericao = BigDecimal.ZERO;

	@NotNull(message = "é obrigatório")
	@Column(name = "NESTOMTANQ", nullable = false, precision = 19, scale = 3)
	private BigDecimal estoque = BigDecimal.ZERO;

	@NotNull(message = "é obrigatório")
	@Column(name = "NPENDMTANQ", nullable = false, precision = 19, scale = 3)
	private BigDecimal pendencia = BigDecimal.ZERO;

	@NotNull(message = "é obrigatório")
	@Column(name = "NSALDMTANQ", nullable = false, precision = 19, scale = 3)
	private BigDecimal saldo = BigDecimal.ZERO;

	@JsonFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	@NotNull(message = "é obrigatório")
	@Column(name = "DDATAMTANQ", nullable = false)
	private Date data = new Date();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Tanque getTanque() {
		return tanque;
	}

	public void setTanque(Tanque tanque) {
		this.tanque = tanque;
	}

	public MedidaTanque getMedidaTanque() {
		return medidaTanque;
	}

	public void setMedidaTanque(MedidaTanque medidaTanque) {
		this.medidaTanque = medidaTanque;
	}

	public BigDecimal getAfericao() {
		return afericao;
	}

	public void setAfericao(BigDecimal afericao) {
		this.afericao = afericao;
	}

	public BigDecimal getEstoque() {
		return estoque;
	}

	public void setEstoque(BigDecimal estoque) {
		this.estoque = estoque;
	}

	public BigDecimal getPendencia() {
		return pendencia;
	}

	public void setPendencia(BigDecimal pendencia) {
		this.pendencia = pendencia;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
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
		MovimentacaoTanque other = (MovimentacaoTanque) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MovimentacaoTanque [id=" + id + ", tanque=" + tanque + ", medidaTanque=" + medidaTanque + ", afericao="
				+ afericao + ", estoque=" + estoque + ", pendencia=" + pendencia + ", saldo=" + saldo + ", data=" + data
				+ "]";
	}

}
