package com.evolution.repository.receber;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.evolution.model.receber.ProcessoFolha;

@Transactional
public interface ProcessoFolhaRepository extends JpaRepository<ProcessoFolha, Long> {

}
