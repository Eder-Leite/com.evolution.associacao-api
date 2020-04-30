package com.evolution.repository.cadastro;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.evolution.model.cadastro.Filial;
import com.evolution.repository.cadastro.filial.FilialRepositoryQuery;

@Transactional
public interface FilialRepository extends JpaRepository<Filial, Long>, FilialRepositoryQuery {

}
