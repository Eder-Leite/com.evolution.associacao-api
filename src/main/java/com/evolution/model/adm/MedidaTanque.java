package com.evolution.model.adm;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ADMMTANQ")
public class MedidaTanque {

	@Id
	@Column(name = "NCODIMTANQ", nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ADM_SADMMTANQ")
	@SequenceGenerator(sequenceName = "ADM_SADMMTANQ", allocationSize = 1, name = "ADM_SADMMTANQ")
	private Long id;

	@ManyToOne
	@NotNull(message = "é obrigatório")
	@JoinColumn(name = "NCODITANQU", nullable = false)
	private Tanque tanque;

	@NotNull(message = "é obrigatório")
	@Column(name = "NNUMRMTANQ", nullable = false)
	private Long numeroRegua;

	@NotNull(message = "é obrigatório")
	@Column(name = "NQUANMTANQ", nullable = false, precision = 19, scale = 3)
	private BigDecimal quantidade = BigDecimal.ZERO;

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

	public Long getNumeroRegua() {
		return numeroRegua;
	}

	public void setNumeroRegua(Long numeroRegua) {
		this.numeroRegua = numeroRegua;
	}

	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
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
		MedidaTanque other = (MedidaTanque) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MedidaTanque [id=" + id + ", tanque=" + tanque + ", numeroRegua=" + numeroRegua + ", quantidade="
				+ quantidade + "]";
	}

}
