package com.evolution.repository.seguranca;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.evolution.model.seguranca.Usuario;
import com.evolution.model.seguranca.enumerador.Status;
import com.evolution.repository.seguranca.usuario.UsuarioRepositoryQuery;

@Transactional
public interface UsuarioRepository extends JpaRepository<Usuario, Long>, UsuarioRepositoryQuery {

	public Optional<Usuario> findByEmailAndStatus(String email, Status status);

	public Usuario findByEmail(String email);

}
