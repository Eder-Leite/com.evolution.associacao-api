package com.evolution.repository.cadastro;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.evolution.model.cadastro.Empresa;
import com.evolution.repository.cadastro.empresa.EmpresaRepositoryQuery;

@Transactional
public interface EmpresaRepository extends JpaRepository<Empresa, Long>, EmpresaRepositoryQuery {

}
