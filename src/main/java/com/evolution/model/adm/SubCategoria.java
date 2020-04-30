package com.evolution.model.adm;

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
@Table(name = "ADMSBCAT")
public class SubCategoria {

	@Id
	@Column(name = "NCODISBCAT", nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ADM_SADMSBCAT")
	@SequenceGenerator(sequenceName = "ADM_SADMSBCAT", allocationSize = 1, name = "ADM_SADMSBCAT")
	private Long id;

	@ManyToOne
	@NotNull(message = "é obrigatório")
	@JoinColumn(name = "NCODICATEG", nullable = false)
	private Categoria categoria;

	@NotBlank(message = "é obrigatório")
	@Column(name = "CDESCSBCAT", nullable = false, length = 255)
	private String descricao;

	@NotNull(message = "é obrigatório")
	@Enumerated(EnumType.STRING)
	@Column(name = "CSTATSBCAT", nullable = false, length = 100)
	private Status status = Status.ATIVO;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
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
		SubCategoria other = (SubCategoria) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SubCategoria [id=" + id + ", categoria=" + categoria + ", descricao=" + descricao + ", status=" + status
				+ "]";
	}

}
