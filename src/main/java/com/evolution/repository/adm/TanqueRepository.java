package com.evolution.repository.adm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.evolution.model.adm.Tanque;
import com.evolution.repository.adm.tanque.TanqueRepositoryQuery;

@Transactional
public interface TanqueRepository extends JpaRepository<Tanque, Long>, TanqueRepositoryQuery {

}
