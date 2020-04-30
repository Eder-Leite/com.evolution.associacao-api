package com.evolution.model.cadastro;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.evolution.model.cadastro.enumerador.TipoPessoa;

@Entity
@Table(name = "CADGERAL")
public class Cadastro {

	@Id
	@Column(name = "NCODIGERAL", nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CAD_SCADGERAL")
	@SequenceGenerator(sequenceName = "CAD_SCADGERAL", allocationSize = 1, name = "CAD_SCADGERAL")
	private Long id;

	@NotNull(message = "é obrigatório")
	@Enumerated(EnumType.STRING)
	@Column(name = "CTPPEGERAL", nullable = false, length = 100)
	private TipoPessoa tipoPessoa;

	@NotBlank(message = "é obrigatório")
	@Column(name = "CDOCUGERAL", nullable = false, length = 18)
	private String cnpjCpf;

	@NotBlank(message = "é obrigatório")
	@Column(name = "CNOMEGERAL", nullable = false, length = 255)
	private String nomeRazaoSocial;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipoPessoa getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(TipoPessoa tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public String getCnpjCpf() {
		return cnpjCpf;
	}

	public void setCnpjCpf(String cnpjCpf) {
		this.cnpjCpf = cnpjCpf;
	}

	public String getNomeRazaoSocial() {
		return nomeRazaoSocial;
	}

	public void setNomeRazaoSocial(String nomeRazaoSocial) {
		this.nomeRazaoSocial = nomeRazaoSocial;
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
		Cadastro other = (Cadastro) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Cadastro [id=" + id + ", tipoPessoa=" + tipoPessoa + ", cnpjCpf=" + cnpjCpf + ", nomeRazaoSocial="
				+ nomeRazaoSocial + "]";
	}

}
