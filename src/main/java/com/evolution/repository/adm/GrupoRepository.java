package com.evolution.repository.adm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.evolution.model.adm.Grupo;
import com.evolution.repository.adm.grupo.GrupoRepositoryQuery;

@Transactional
public interface GrupoRepository extends JpaRepository<Grupo, Long>, GrupoRepositoryQuery {

}
