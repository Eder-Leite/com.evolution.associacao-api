package com.evolution.repository.adm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.evolution.model.adm.MedidaTanque;
import com.evolution.repository.adm.medidaTanque.MedidaTanqueRepositoryQuery;

@Transactional
public interface MedidaTanqueRepository extends JpaRepository<MedidaTanque, Long>, MedidaTanqueRepositoryQuery {

}
