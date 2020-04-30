package com.evolution.repository.seguranca;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.evolution.model.seguranca.Departamento;

@Transactional
public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {

}
