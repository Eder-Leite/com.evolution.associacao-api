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

import org.hibernate.annotations.ForeignKey;
import org.hibernate.validator.constraints.NotBlank;

@SuppressWarnings("deprecation")
@Entity
@Table(name = "CADFILIA")
public class Filial {

	@Id
	@Column(name = "NCODIFILIA", nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CAD_SCADFILIA")
	@SequenceGenerator(sequenceName = "CAD_SCADFILIA", allocationSize = 1, name = "CAD_SCADFILIA")
	private Long id;

	@ManyToOne
	@NotNull(message = "é obrigatório")
	@ForeignKey(name = "FK_CADFILIA_CADEMPRE")
	@JoinColumn(name = "NCODIEMPRE", nullable = false)
	private Empresa empresa;

	@NotBlank(message = "é obrigatório")
	@Column(name = "CCODIFILIA", nullable = false, length = 3)
	private String codigo;

	@NotBlank(message = "é obrigatório")
	@Column(name = "CNOMEFILIA", nullable = false, length = 255)
	private String nome;

	@NotBlank(message = "é obrigatório")
	@Column(name = "CCIDAFILIA", nullable = false, length = 255)
	private String cidade;

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

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
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
		Filial other = (Filial) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Filial [id=" + id + ", empresa=" + empresa + ", codigo=" + codigo + ", nome=" + nome + ", cidade="
				+ cidade + "]";
	}

}
