package com.evolution.model.faturamento;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ForeignKey;

import com.evolution.model.cadastro.Empresa;
import com.evolution.model.cadastro.Evento;
import com.evolution.model.cadastro.Funcionario;
import com.evolution.model.cadastro.Periodo;
import com.evolution.model.cadastro.TipoDesconto;
import com.evolution.model.seguranca.Departamento;
import com.evolution.model.seguranca.Usuario;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "FAT_NOTA")
@SuppressWarnings("deprecation")
public class Nota {

	@Id
	@Column(name = "NCODI_NOTA", nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FAT_SFAT_NOTA")
	@SequenceGenerator(sequenceName = "FAT_SFAT_NOTA", allocationSize = 1, name = "FAT_SFAT_NOTA")
	private Long id;

	@ManyToOne
	@NotNull(message = "é obrigatório")
	@ForeignKey(name = "FK_CADFUNCI_CADEMPRE")
	@JoinColumn(name = "NCODIEMPRE", nullable = false)
	private Empresa empresa;

	@ManyToOne
	@NotNull(message = "é obrigatório")
	@ForeignKey(name = "FK_FAT_NOTA_SEGDEPAR")
	@JoinColumn(name = "NCODIDEPAR", nullable = false)
	private Departamento departamento;

	@ManyToOne
	@NotNull(message = "é obrigatório")
	@ForeignKey(name = "FK_FAT_NOTA_CADTPDES")
	@JoinColumn(name = "NCODITPDES", nullable = false)
	private TipoDesconto tipoDesconto;

	@ManyToOne
	@NotNull(message = "é obrigatório")
	@ForeignKey(name = "FK_FAT_NOTA_CADEVENT")
	@JoinColumn(name = "NCODIEVENT", nullable = false)
	private Evento evento;

	@ManyToOne
	@NotNull(message = "é obrigatório")
	@ForeignKey(name = "FK_FAT_NOTA_CADPERIO")
	@JoinColumn(name = "NCODIPERIO", nullable = false)
	private Periodo periodo;

	@ManyToOne
	@NotNull(message = "é obrigatório")
	@ForeignKey(name = "FK_FAT_NOTA_CADFUNCI")
	@JoinColumn(name = "NCODIFUNCI", nullable = false)
	private Funcionario funcionario;

	@ManyToOne
	@NotNull(message = "é obrigatório")
	@ForeignKey(name = "FK_FAT_NOTA_SEGUSUAR")
	@JoinColumn(name = "NCODIUSUAR", nullable = false)
	private Usuario usuario;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull(message = "é obrigatório")
	@Column(name = "DDATA_NOTA", nullable = false)
	private Date dataNota = new Date();

	@NotNull(message = "é obrigatório")
	@Column(name = "CNPAR_NOTA", nullable = false, length = 15)
	private String numeroParcela = "PARCELA 1/1";

	@NotNull(message = "é obrigatório")
	@Column(name = "NVALO_NOTA", nullable = false, precision = 19, scale = 2)
	private BigDecimal valorNota = BigDecimal.ZERO;

	@Column(name = "NQFDS_NOTA", nullable = false)
	private Long quantidadeFalhaDigitarSenha = 0l;

	@ManyToOne
	@NotNull(message = "é obrigatório")
	@ForeignKey(name = "FK_FAT_NOTA_FATCODRN")
	@JoinColumn(name = "NCODICODRN", nullable = false)
	private CodigoRetornoNota codigoRetornoNota;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull(message = "é obrigatório")
	@Column(name = "DDTAU_NOTA")
	private Date dataAutenticacao;

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

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public TipoDesconto getTipoDesconto() {
		return tipoDesconto;
	}

	public void setTipoDesconto(TipoDesconto tipoDesconto) {
		this.tipoDesconto = tipoDesconto;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public Periodo getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Date getDataNota() {
		return dataNota;
	}

	public void setDataNota(Date dataNota) {
		this.dataNota = dataNota;
	}

	public String getNumeroParcela() {
		return numeroParcela;
	}

	public void setNumeroParcela(String numeroParcela) {
		this.numeroParcela = numeroParcela;
	}

	public BigDecimal getValorNota() {
		return valorNota;
	}

	public void setValorNota(BigDecimal valorNota) {
		this.valorNota = valorNota;
	}

	public Long getQuantidadeFalhaDigitarSenha() {
		return quantidadeFalhaDigitarSenha;
	}

	public void setQuantidadeFalhaDigitarSenha(Long quantidadeFalhaDigitarSenha) {
		this.quantidadeFalhaDigitarSenha = quantidadeFalhaDigitarSenha;
	}

	public CodigoRetornoNota getCodigoRetornoNota() {
		return codigoRetornoNota;
	}

	public void setCodigoRetornoNota(CodigoRetornoNota codigoRetornoNota) {
		this.codigoRetornoNota = codigoRetornoNota;
	}

	public Date getDataAutenticacao() {
		return dataAutenticacao;
	}

	public void setDataAutenticacao(Date dataAutenticacao) {
		this.dataAutenticacao = dataAutenticacao;
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
		Nota other = (Nota) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Nota [id=" + id + ", empresa=" + empresa + ", departamento=" + departamento + ", tipoDesconto="
				+ tipoDesconto + ", evento=" + evento + ", periodo=" + periodo + ", usuario=" + usuario
				+ ", funcionario=" + funcionario + ", dataNota=" + dataNota + ", numeroParcela=" + numeroParcela
				+ ", valorNota=" + valorNota + ", quantidadeFalhaDigitarSenha=" + quantidadeFalhaDigitarSenha
				+ ", codigoRetornoNota=" + codigoRetornoNota + ", dataAutenticacao=" + dataAutenticacao + "]";
	}

}
