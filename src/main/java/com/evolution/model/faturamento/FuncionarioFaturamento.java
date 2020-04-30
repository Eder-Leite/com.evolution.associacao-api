package com.evolution.model.faturamento;

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

import com.evolution.model.cadastro.FuncaoTrabalho;
import com.evolution.model.faturamento.enumerador.Status;

@Entity
@Table(name = "FATFUNCI")
public class FuncionarioFaturamento {

	@Id
	@Column(name = "NCODIFUNCI", nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FAT_SFATFUNCI")
	@SequenceGenerator(sequenceName = "FAT_SFATFUNCI", allocationSize = 1, name = "FAT_SFATFUNCI")
	private Long id;

	@ManyToOne
	@NotNull(message = "é obrigatório")
	@JoinColumn(name = "NCODIFUNTR", nullable = false)
	private FuncaoTrabalho funcaoTrabalho;

	@NotBlank(message = "é obrigatório")
	@Column(name = "CNOMEFUNCI", nullable = false, length = 255)
	private String nome;

	@NotNull(message = "é obrigatório")
	@Enumerated(EnumType.STRING)
	@Column(name = "CSTATFUNCI", nullable = false, length = 100)
	private Status status = Status.ATIVO;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FuncaoTrabalho getFuncaoTrabalho() {
		return funcaoTrabalho;
	}

	public void setFuncaoTrabalho(FuncaoTrabalho funcaoTrabalho) {
		this.funcaoTrabalho = funcaoTrabalho;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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
		FuncionarioFaturamento other = (FuncionarioFaturamento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Funcionario [id=" + id + ", funcaoTrabalho=" + funcaoTrabalho + ", nome=" + nome + ", status=" + status
				+ "]";
	}

}
