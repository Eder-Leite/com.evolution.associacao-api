package com.evolution.repository.seguranca.filter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.evolution.model.seguranca.Permissao;
import com.evolution.model.seguranca.Usuario;
import com.evolution.model.seguranca.enumerador.Status;

public class PerfilUsuario {

	private Long id;
	private String departamento;
	private String nome;
	private String email;
	private String senhaAntiga;
	private String senhaNova;
	private Status status;
	private Long quantidadeAcesso;
	private Date dataUltimoAcesso;
	private Date dataCadastro;
	private List<Permissao> permissoes = new ArrayList<>();

	public PerfilUsuario() {
		super();

	}

	public PerfilUsuario(Long id, String departamento, String nome, String email, String senhaAntiga, String senhaNova,
			Status status, Long quantidadeAcesso, Date dataUltimoAcesso, Date dataCadastro,
			List<Permissao> permissoes) {

		this.id = id;
		this.departamento = departamento;
		this.nome = nome;
		this.email = email;
		this.senhaAntiga = senhaAntiga;
		this.senhaNova = senhaNova;
		this.status = status;
		this.quantidadeAcesso = quantidadeAcesso;
		this.dataUltimoAcesso = dataUltimoAcesso;
		this.dataCadastro = dataCadastro;
		this.permissoes = permissoes;
	}

	public PerfilUsuario(Usuario usuario) {

		if (usuario != null) {
			this.id = usuario.getId();
			this.departamento = usuario.getDepartamento().getNome();
			this.nome = usuario.getNome();
			this.email = usuario.getEmail();
			this.senhaAntiga = usuario.getSenha();
			this.senhaNova = usuario.getSenha();
			this.status = usuario.getStatus();
			this.quantidadeAcesso = usuario.getQuantidadeAcesso();
			this.dataUltimoAcesso = usuario.getDataUltimoAcesso();
			this.dataCadastro = usuario.getDataCadastro();
			this.permissoes = usuario.getPermissoes();
		}

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
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

	public String getSenhaAntiga() {
		return senhaAntiga;
	}

	public void setSenhaAntiga(String senhaAntiga) {
		this.senhaAntiga = senhaAntiga;
	}

	public String getSenhaNova() {
		return senhaNova;
	}

	public void setSenhaNova(String senhaNova) {
		this.senhaNova = senhaNova;
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

}
