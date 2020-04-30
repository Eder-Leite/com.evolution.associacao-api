package com.evolution.model.adm;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.evolution.model.adm.enumerador.Status;

@Entity
@Table(name = "ADMTANQU")
public class Tanque {

	@Id
	@Column(name = "NCODITANQU", nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ADM_SADMTANQU")
	@SequenceGenerator(sequenceName = "ADM_SADMTANQU", allocationSize = 1, name = "ADM_SADMTANQU")
	private Long id;

	@ManyToOne
	@NotNull(message = "é obrigatório")
	@JoinColumn(name = "NCODIPRODU", nullable = false)
	private Produto produto;

	@NotBlank(message = "é obrigatório")
	@Column(name = "CNUMETANQU", nullable = false, length = 10)
	private String numero;

	@NotBlank(message = "é obrigatório")
	@Column(name = "CDESCTANQU", nullable = false, length = 255)
	private String descricao;

	@NotNull(message = "é obrigatório")
	@Column(name = "NQUANTANQU", nullable = false, precision = 19, scale = 3)
	private BigDecimal quantidade = BigDecimal.ZERO;

	@NotNull(message = "é obrigatório")
	@Enumerated(EnumType.STRING)
	@Column(name = "CSTATTANQU", nullable = false, length = 100)
	private Status status = Status.ATIVO;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
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
		Tanque other = (Tanque) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Tanque [id=" + id + ", produto=" + produto + ", numero=" + numero + ", descricao=" + descricao
				+ ", quantidade=" + quantidade + ", status=" + status + "]";
	}

}
