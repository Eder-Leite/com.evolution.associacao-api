package com.evolution.model.faturamento;

import java.math.BigDecimal;

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

import com.evolution.model.adm.Produto;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "FATITEOS")
@JsonIdentityInfo(property = "id", generator = ObjectIdGenerators.PropertyGenerator.class)
public class ItemOrdemServico {

	@Id
	@Column(name = "NCODIITEOS", nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FAT_SFATITEOS")
	@SequenceGenerator(sequenceName = "FAT_SFATITEOS", allocationSize = 1, name = "FAT_SFATITEOS")
	private Long id;

	@ManyToOne
	@NotNull(message = "é obrigatório")
	@JoinColumn(name = "NCODIORDSE", nullable = false)
	private OrdemServico ordemServico;

	@ManyToOne
	@NotNull(message = "é obrigatório")
	@JoinColumn(name = "NCODIFUNCI", nullable = false)
	private FuncionarioFaturamento funcionario;

	@ManyToOne
	@NotNull(message = "é obrigatório")
	@JoinColumn(name = "NCODIPRODU", nullable = false)
	private Produto produto;

	@NotNull(message = "é obrigatório")
	@Column(name = "NQUANITEOS", nullable = false, precision = 19, scale = 3)
	private BigDecimal quantidade = BigDecimal.ONE;

	@NotNull(message = "é obrigatório")
	@Column(name = "NVLRUITEOS", nullable = false, precision = 19, scale = 3)
	private BigDecimal valorUnitario = BigDecimal.ZERO;

	@NotNull(message = "é obrigatório")
	@Column(name = "NVLRTITEOS", nullable = false, precision = 19, scale = 3)
	private BigDecimal valorTotal = BigDecimal.ZERO;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OrdemServico getOrdemServico() {
		return ordemServico;
	}

	public void setOrdemServico(OrdemServico ordemServico) {
		this.ordemServico = ordemServico;
	}

	public FuncionarioFaturamento getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(FuncionarioFaturamento funcionario) {
		this.funcionario = funcionario;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
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
		ItemOrdemServico other = (ItemOrdemServico) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ItemOrdemServico [id=" + id + ", ordemServico=" + ordemServico + ", funcionario=" + funcionario
				+ ", produto=" + produto + ", quantidade=" + quantidade + ", valorUnitario=" + valorUnitario
				+ ", valorTotal=" + valorTotal + "]";
	}

}
