package com.evolution.model.cadastro;

import java.math.BigDecimal;
import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

import com.evolution.model.cadastro.Empresa;
import com.evolution.model.cadastro.enumerador.SituacaoFuncionario;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "CADFUNCI")
@SuppressWarnings("deprecation")
public class Funcionario {

	@Id
	@Column(name = "NCODIFUNCI", nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CAD_SCADFUNCI")
	@SequenceGenerator(sequenceName = "CAD_SCADFUNCI", allocationSize = 1, name = "CAD_SCADFUNCI")
	private Long id;

	@ManyToOne
	@NotNull(message = "é obrigatório")
	@ForeignKey(name = "FK_CADFUNCI_CADEMPRE")
	@JoinColumn(name = "NCODIEMPRE", nullable = false)
	private Empresa empresa;

	@ManyToOne
	@NotNull(message = "é obrigatório")
	@ForeignKey(name = "FK_CADFUNCI_CADFILIA")
	@JoinColumn(name = "NCODIFILIA", nullable = false)
	private Filial filial;

	@NotBlank(message = "é obrigatório")
	@Column(name = "CNOMEFUNCI", nullable = false, length = 100)
	private String nomeFuncionario;

	@CPF(message = "Atenção o CPF informado não é valido")
	@NotBlank(message = "é obrigatório")
	@Column(name = "CNCPFFUNCI", nullable = false, length = 14)
	private String cpf;

	@NotBlank(message = "é obrigatório")
	@Column(name = "CCRACFUNCI", nullable = false, length = 10)
	private String cracha;

	@Email(message = "atenção o e-mail informado não atende os padrões")
	@NotBlank(message = "é obrigatório")
	@Column(name = "CEMAIFUNCI", unique = true, nullable = false, length = 255)
	private String email;

	@JsonIgnore
	@NotBlank(message = "é obrigatório")
	@Column(name = "CSENHFUNCI", nullable = false, length = 255)
	private String senha;

	@NotNull(message = "é obrigatório")
	@Enumerated(EnumType.STRING)
	@Column(name = "CSITUFUNCI", nullable = false, length = 15)
	private SituacaoFuncionario situacaoFuncionario = SituacaoFuncionario.CADASTRANDO;

	@NotNull(message = "é obrigatório")
	@Column(name = "NVLIMFUNCI", nullable = false, precision = 19, scale = 2)
	private BigDecimal valorLimite = BigDecimal.ZERO;

	@Column(name = "NQTFLFUNCI")
	private Long quantidadeFalhaLogin = 0l;

	@Column(name = "NQTACFUNCI")
	private Long quantidadeAcesso = 0l;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DDTUAFUNCI")
	private Date dataUltimoAcesso = new Date();

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DDTCAFUNCI")
	private Date dataCadastro = new Date();

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

	public Filial getFilial() {
		return filial;
	}

	public void setFilial(Filial filial) {
		this.filial = filial;
	}

	public String getNomeFuncionario() {
		return nomeFuncionario;
	}

	public void setNomeFuncionario(String nomeFuncionario) {
		this.nomeFuncionario = nomeFuncionario;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getCracha() {
		return cracha;
	}

	public void setCracha(String cracha) {
		this.cracha = cracha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public SituacaoFuncionario getSituacaoFuncionario() {
		return situacaoFuncionario;
	}

	public void setSituacaoFuncionario(SituacaoFuncionario situacaoFuncionario) {
		this.situacaoFuncionario = situacaoFuncionario;
	}

	public BigDecimal getValorLimite() {
		return valorLimite;
	}

	public void setValorLimite(BigDecimal valorLimite) {
		this.valorLimite = valorLimite;
	}

	public Long getQuantidadeFalhaLogin() {
		return quantidadeFalhaLogin;
	}

	public void setQuantidadeFalhaLogin(Long quantidadeFalhaLogin) {
		this.quantidadeFalhaLogin = quantidadeFalhaLogin;
	}

	public Long getQuantidadeAcesso() {
		return quantidadeAcesso;
	}

	public void setQuantidadeAcesso(Long quantidadeAcesso) {
		this.quantidadeAcesso = quantidadeAcesso;
	}

	public Date getDataUltimoAcesso() {
		return dataUltimoAcesso;
	}

	public void setDataUltimoAcesso(Date dataUltimoAcesso) {
		this.dataUltimoAcesso = dataUltimoAcesso;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
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
		Funcionario other = (Funcionario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Funcionario [id=" + id + ", empresa=" + empresa + ", filial=" + filial + ", nomeFuncionario="
				+ nomeFuncionario + ", cpf=" + cpf + ", cracha=" + cracha + ", email=" + email + ", senha=" + senha
				+ ", situacaoFuncionario=" + situacaoFuncionario + ", valorLimite=" + valorLimite
				+ ",  quantidadeFalhaLogin=" + quantidadeFalhaLogin + ", quantidadeAcesso=" + quantidadeAcesso
				+ ", dataUltimoAcesso=" + dataUltimoAcesso + ", dataCadastro=" + dataCadastro + "]";
	}

}