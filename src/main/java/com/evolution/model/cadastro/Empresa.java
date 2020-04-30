package com.evolution.model.cadastro;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "CADEMPRE")
public class Empresa {

	@Id
	@Column(name = "NCODIEMPRE", nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CAD_SCADEMPRE")
	@SequenceGenerator(sequenceName = "CAD_SCADEMPRE", allocationSize = 1, name = "CAD_SCADEMPRE")
	private Long id;

	@NotBlank(message = "é obrigatório")
	@Column(name = "CNOMEEMPRE", nullable = false, length = 100)
	private String nomeEmpresa;

	@NotBlank(message = "é obrigatório")
	@Column(name = "CFANTEMPRE", nullable = false, length = 100)
	private String nomeFantasia;

	@NotBlank(message = "é obrigatório")
	@Column(name = "CCNPJEMPRE", unique = true, nullable = false, length = 18)
	private String cnpj;

	@NotBlank(message = "é obrigatório")
	@Column(name = "CNAFUEMPRE", nullable = false, length = 100)
	private String nomeAssociacao;

	public Empresa() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Empresa(Long id, String nomeEmpresa, String nomeFantasia, String cnpj, String nomeAssociacao) {
		super();
		this.id = id;
		this.nomeEmpresa = nomeEmpresa;
		this.nomeFantasia = nomeFantasia;
		this.cnpj = cnpj;
		this.nomeAssociacao = nomeAssociacao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeEmpresa() {
		return nomeEmpresa;
	}

	public void setNomeEmpresa(String nomeEmpresa) {
		this.nomeEmpresa = nomeEmpresa;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getNomeAssociacao() {
		return nomeAssociacao;
	}

	public void setNomeAssociacao(String nomeAssociacao) {
		this.nomeAssociacao = nomeAssociacao;
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
		Empresa other = (Empresa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Empresa [id=" + id + ", nomeEmpresa=" + nomeEmpresa + ", nomeFantasia=" + nomeFantasia + ", cnpj="
				+ cnpj + ", nomeAssociacao=" + nomeAssociacao + "]";
	}

}
