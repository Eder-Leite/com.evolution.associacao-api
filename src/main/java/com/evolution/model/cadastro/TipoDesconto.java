package com.evolution.model.cadastro;

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

import org.hibernate.annotations.ForeignKey;
import org.hibernate.validator.constraints.NotBlank;

import com.evolution.model.cadastro.enumerador.Check;
import com.evolution.model.seguranca.Departamento;

@Entity
@Table(name = "CADTPDES")
@SuppressWarnings("deprecation")
public class TipoDesconto {

	@Id
	@Column(name = "NCODITPDES", nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CAD_SCADTPDES")
	@SequenceGenerator(sequenceName = "CAD_SCADTPDES", allocationSize = 1, name = "CAD_SCADTPDES")
	private Long id;

	@ManyToOne
	@NotNull(message = "é obrigatório")
	@ForeignKey(name = "FK_CADTPDES_SEGDEPAR")
	@JoinColumn(name = "NCODIDEPAR", nullable = false)
	private Departamento departamento;

	@NotBlank(message = "é obrigatório")
	@Column(name = "CDESCTPDES", nullable = false, length = 255)
	private String descricao;

	@NotNull(message = "é obrigatório")
	@Enumerated(EnumType.STRING)
	@Column(name = "CVISITPDES", nullable = false, length = 15)
	private Check visivel = Check.NÃO;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Check getVisivel() {
		return visivel;
	}

	public void setVisivel(Check visivel) {
		this.visivel = visivel;
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
		TipoDesconto other = (TipoDesconto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TipoDesconto [id=" + id + ", departamento=" + departamento + ", descricao=" + descricao + ", visivel="
				+ visivel + "]";
	}

}
