package com.evolution.model.cadastro;

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

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "CADFUNTR")
public class FuncaoTrabalho {

	@Id
	@Column(name = "NCODIFUNTR", nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CAD_SCADFUNTR")
	@SequenceGenerator(sequenceName = "CAD_SCADFUNTR", allocationSize = 1, name = "CAD_SCADFUNTR")
	private Long id;

	@ManyToOne
	@NotNull(message = "é obrigatório")
	@JoinColumn(name = "NCODISETOR", nullable = false)
	private Setor setor;

	@NotBlank(message = "é obrigatório")
	@Column(name = "CDESCFUNTR", nullable = false, length = 255)
	private String descricao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Setor getSetor() {
		return setor;
	}

	public void setSetor(Setor setor) {
		this.setor = setor;
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
		FuncaoTrabalho other = (FuncaoTrabalho) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FuncaoTrabalho [id=" + id + ", setor=" + setor + ", descricao=" + descricao + "]";
	}

}
