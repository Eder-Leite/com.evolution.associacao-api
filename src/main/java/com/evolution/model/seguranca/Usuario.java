package com.evolution.model.seguranca;

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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import com.evolution.model.seguranca.enumerador.Status;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;

@Entity
@Table(name = "SEGUSUAR")
@SuppressWarnings("deprecation")
@JsonIdentityInfo(property = "id", generator = ObjectIdGenerators.PropertyGenerator.class)
public class Usuario {

	@Id
	@Column(name = "NCODIUSUAR", nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEG_SSEGUSUAR")
	@SequenceGenerator(sequenceName = "SEG_SSEGUSUAR", allocationSize = 1, name = "SEG_SSEGUSUAR")
	private Long id;

	@ManyToOne
	@NotNull(message = "é obrigatório")
	@ForeignKey(name = "FK_SEGUSUAR_CADDEPAR")
	@JoinColumn(name = "NCODIDEPAR", nullable = false)
	private Departamento departamento;

	@NotBlank(message = "é obrigatório")
	@Column(name = "CNOMEUSUAR", nullable = false, length = 100)
	private String nome;

	@Email(message = "atenção o e-mail informado não atende os padrões")
	@NotBlank(message = "é obrigatório")
	@Column(name = "CEMAIUSUAR", unique = true, nullable = false, length = 255)
	private String email;

	@JsonProperty(access = Access.WRITE_ONLY)
	@NotBlank(message = "é obrigatório")
	@Column(name = "CSENHUSUAR", nullable = false, length = 255)
	private String senha;

	@NotNull(message = "é obrigatório")
	@Enumerated(EnumType.STRING)
	@Column(name = "CSTATUSUAR", nullable = false, length = 15)
	private Status status = Status.ATIVO;

	@Column(name = "NQTACUSUAR")
	private Long quantidadeAcesso = 0l;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DDTUAUSUAR")
	private Date dataUltimoAcesso = new Date();

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DDTCAUSUAR")
	private Date dataCadastro = new Date();

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "SEGPERUS", joinColumns = @JoinColumn(name = "NCODIUSUAR"), inverseJoinColumns = @JoinColumn(name = "NCODIPERMU"))
	private List<Permissao> permissoes;

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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
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

	public List<Permissao> getPermissoes() {
		return permissoes;
	}

	public void setPermissoes(List<Permissao> permissoes) {
		this.permissoes = permissoes;
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
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", departamento=" + departamento + ", nome=" + nome + ", email=" + email
				+ ", senha=" + senha + ", status=" + status + ", quantidadeAcesso=" + quantidadeAcesso
				+ ", dataUltimoAcesso=" + dataUltimoAcesso + ", dataCadastro=" + dataCadastro + ", permissoes="
				+ permissoes + "]";
	}

}