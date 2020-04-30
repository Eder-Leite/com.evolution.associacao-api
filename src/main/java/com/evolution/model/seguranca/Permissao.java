package com.evolution.model.seguranca;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "SEGPERMU")
public class Permissao {

	@Id
	@Column(name = "NCODIPERMU", nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEG_SSEGPERMU")
	@SequenceGenerator(sequenceName = "SEG_SSEGPERMU", allocationSize = 1, name = "SEG_SSEGPERMU")
	private Long id;

	@NotBlank(message = "é obrigatório")
	@Column(name = "CDESCPERMU", nullable = false, length = 500)
	private String descricao;

	@NotBlank(message = "é obrigatório")
	@Column(name = "CSIGLPERMU", unique = true, nullable = false, length = 255)
	private String sigla;

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

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
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
		Permissao other = (Permissao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Permissao [id=" + id + ", descricao=" + descricao + ", sigla=" + sigla + "]";
	}

}
