package com.evolution.model.faturamento;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "FATCODRN")
public class CodigoRetornoNota {

	@Id
	@Column(name = "NCODICODRN", nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FAT_SFATCODRN")
	@SequenceGenerator(sequenceName = "FAT_SFATCODRN", allocationSize = 1, name = "FAT_SFATCODRN")
	private Long id;

	@NotBlank(message = "é obrigatório")
	@Column(name = "CDESCCODRN", nullable = false, length = 100)
	private String descricao;

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
		CodigoRetornoNota other = (CodigoRetornoNota) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CodigoRetornoNota [id=" + id + ", descricao=" + descricao + "]";
	}

}
