package com.evolution.model.faturamento;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.evolution.model.cadastro.Cadastro;
import com.evolution.model.cadastro.Veiculo;
import com.evolution.model.faturamento.enumerador.Cortesia;
import com.evolution.model.faturamento.enumerador.SituacaoOrdemServico;
import com.evolution.model.seguranca.Usuario;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "FATORDSE")
@JsonIdentityInfo(property = "id", generator = ObjectIdGenerators.PropertyGenerator.class)
public class OrdemServico {

	@Id
	@Column(name = "NCODIORDSE", nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FAT_SFATORDSE")
	@SequenceGenerator(sequenceName = "FAT_SFATORDSE", allocationSize = 1, name = "FAT_SFATORDSE")
	private Long id;

	@ManyToOne
	@NotNull(message = "é obrigatório")
	@JoinColumn(name = "NCODIVEICU", nullable = false)
	private Veiculo veiculo;

	@ManyToOne
	@NotNull(message = "é obrigatório")
	@JoinColumn(name = "NCODIUSUAR", nullable = false)
	private Usuario usuario;

	@ManyToOne
	@NotNull(message = "é obrigatório")
	@JoinColumn(name = "NCODIGERAL", nullable = false)
	private Cadastro cadastro;

	@NotNull(message = "é obrigatório")
	@Enumerated(EnumType.STRING)
	@Column(name = "CSITUORDSE", nullable = false, length = 100)
	private SituacaoOrdemServico situacao = SituacaoOrdemServico.ABERTO;

	@NotBlank(message = "é obrigatório")
	@Column(name = "CPLACORDSE", nullable = false, length = 8)
	private String placa;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull(message = "é obrigatório")
	@Column(name = "DDATAORDSE", nullable = false)
	private Date data = new Date();

	@NotNull(message = "é obrigatório")
	@Column(name = "NVLRDORDSE", nullable = false, precision = 19, scale = 2)
	private BigDecimal valorDesconto = BigDecimal.ZERO;

	@NotNull(message = "é obrigatório")
	@Column(name = "NVLRAORDSE", nullable = false, precision = 19, scale = 2)
	private BigDecimal valorAcrescimo = BigDecimal.ZERO;

	@NotNull(message = "é obrigatório")
	@Column(name = "NVLRPORDSE", nullable = false, precision = 19, scale = 2)
	private BigDecimal valorProduto = BigDecimal.ZERO;

	@NotNull(message = "é obrigatório")
	@Column(name = "NVLRTORDSE", nullable = false, precision = 19, scale = 2)
	private BigDecimal valorTotal = BigDecimal.ZERO;

	@NotBlank(message = "é obrigatório")
	@Column(name = "COBSEORDSE", nullable = false, length = 4000)
	private String observacao;

	@NotNull(message = "é obrigatório")
	@Enumerated(EnumType.STRING)
	@Column(name = "CCORTORDSE", nullable = false, length = 100)
	private Cortesia cortesia = Cortesia.NÃO;

	@OneToMany(mappedBy = "ordemServico", orphanRemoval = true, fetch = FetchType.LAZY)
	private List<ItemOrdemServico> itens = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Veiculo getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Cadastro getCadastro() {
		return cadastro;
	}

	public void setCadastro(Cadastro cadastro) {
		this.cadastro = cadastro;
	}

	public SituacaoOrdemServico getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoOrdemServico situacao) {
		this.situacao = situacao;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public BigDecimal getValorDesconto() {
		return valorDesconto;
	}

	public void setValorDesconto(BigDecimal valorDesconto) {
		this.valorDesconto = valorDesconto;
	}

	public BigDecimal getValorAcrescimo() {
		return valorAcrescimo;
	}

	public void setValorAcrescimo(BigDecimal valorAcrescimo) {
		this.valorAcrescimo = valorAcrescimo;
	}

	public BigDecimal getValorProduto() {
		return valorProduto;
	}

	public void setValorProduto(BigDecimal valorProduto) {
		this.valorProduto = valorProduto;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Cortesia getCortesia() {
		return cortesia;
	}

	public void setCortesia(Cortesia cortesia) {
		this.cortesia = cortesia;
	}

	public List<ItemOrdemServico> getItens() {
		return itens;
	}

	public void setItens(List<ItemOrdemServico> itens) {
		this.itens = itens;
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
		OrdemServico other = (OrdemServico) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OrdemServico [id=" + id + ", veiculo=" + veiculo + ", usuario=" + usuario + ", cadastro=" + cadastro
				+ ", situacao=" + situacao + ", placa=" + placa + ", data=" + data + ", valorDesconto=" + valorDesconto
				+ ", valorAcrescimo=" + valorAcrescimo + ", valorProduto=" + valorProduto + ", valorTotal=" + valorTotal
				+ ", observacao=" + observacao + ", cortesia=" + cortesia + ", itens=" + itens + "]";
	}

}
