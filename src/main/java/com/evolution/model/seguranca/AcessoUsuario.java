package com.evolution.model.seguranca;

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

@Entity
@Table(name = "SEGACESU")
@SuppressWarnings("deprecation")
public class AcessoUsuario {

	@Id
	@Column(name = "NCODIACESU", nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEG_SSEGACESU")
	@SequenceGenerator(sequenceName = "SEG_SSEGACESU", allocationSize = 1, name = "SEG_SSEGACESU")
	private Long id;

	@ManyToOne
	@NotNull(message = "é obrigatório")
	@ForeignKey(name = "FK_SEGACESU_SEGUSUAR")
	@JoinColumn(name = "NCODIUSUAR", nullable = false)
	private Usuario usuario;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull(message = "é obrigatório")
	@Column(name = "DDATAACESU", nullable = false)
	private Date dataAcesso = new Date();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Date getDataAcesso() {
		return dataAcesso;
	}

	public void setDataAcesso(Date dataAcesso) {
		this.dataAcesso = dataAcesso;
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
		AcessoUsuario other = (AcessoUsuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AcessoUsuario [id=" + id + ", usuario=" + usuario + ", dataAcesso=" + dataAcesso + "]";
	}

}
