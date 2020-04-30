package com.evolution.repository.seguranca;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.evolution.model.seguranca.Permissao;

@Transactional
public interface PermissaoRepository extends JpaRepository<Permissao, Long> {

}
