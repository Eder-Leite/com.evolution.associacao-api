package com.evolution.model.adm;

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

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "ADMPRODU")
public class Produto {

	@Id
	@Column(name = "NCODIPRODU", nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ADM_SADMPRODU")
	@SequenceGenerator(sequenceName = "ADM_SADMPRODU", allocationSize = 1, name = "ADM_SADMPRODU")
	private Long id;

	@ManyToOne
	@NotNull(message = "é obrigatório")
	@JoinColumn(name = "NCODISBCAT", nullable = false)
	private SubCategoria subCategoria;

	@ManyToOne
	@NotNull(message = "é obrigatório")
	@JoinColumn(name = "NCODIGRPRO")
	private Grupo grupo;

	@NotBlank(message = "é obrigatório")
	@Column(name = "CDESCPRODU", nullable = false, length = 255)
	private String descricao;

	@NotBlank(message = "é obrigatório")
	@Column(name = "CUNIDPRODU", nullable = false, length = 100)
	private String unidade;

	@NotBlank(message = "é obrigatório")
	@Column(name = "CCBARPRODU", length = 15)
	private String codigoBarra;

	@NotNull(message = "é obrigatório")
	@Column(name = "NCUSTPRODU", nullable = false, precision = 19, scale = 2)
	private BigDecimal valorCusto = BigDecimal.ZERO;

	@NotNull(message = "é obrigatório")
	@Column(name = "NVENDPRODU", nullable = false, precision = 19, scale = 2)
	private BigDecimal valorVenda = BigDecimal.ZERO;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SubCategoria getSubCategoria() {
		return subCategoria;
	}

	public void setSubCategoria(SubCategoria subCategoria) {
		this.subCategoria = subCategoria;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	public String getCodigoBarra() {
		return codigoBarra;
	}

	public void setCodigoBarra(String codigoBarra) {
		this.codigoBarra = codigoBarra;
	}

	public BigDecimal getValorCusto() {
		return valorCusto;
	}

	public void setValorCusto(BigDecimal valorCusto) {
		this.valorCusto = valorCusto;
	}

	public BigDecimal getValorVenda() {
		return valorVenda;
	}

	public void setValorVenda(BigDecimal valorVenda) {
		this.valorVenda = valorVenda;
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
		Produto other = (Produto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Produto [id=" + id + ", subCategoria=" + subCategoria + ", grupo=" + grupo + ", descricao=" + descricao
				+ ", unidade=" + unidade + ", codigoBarra=" + codigoBarra + ", valorCusto=" + valorCusto
				+ ", valorVenda=" + valorVenda + "]";
	}

}
