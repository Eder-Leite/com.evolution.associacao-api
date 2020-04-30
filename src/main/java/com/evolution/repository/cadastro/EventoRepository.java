package com.evolution.repository.cadastro;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.evolution.model.cadastro.Evento;
import com.evolution.repository.cadastro.evento.EventoRepositoryQuery;

@Transactional
public interface EventoRepository extends JpaRepository<Evento, Long>, EventoRepositoryQuery {

}
