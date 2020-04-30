package com.evolution.repository.seguranca;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.evolution.model.seguranca.AcessoUsuario;

@Transactional
public interface AcessoUsuarioRepository extends JpaRepository<AcessoUsuario, Long> {

}
