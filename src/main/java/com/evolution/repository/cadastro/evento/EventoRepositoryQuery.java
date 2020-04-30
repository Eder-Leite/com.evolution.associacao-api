package com.evolution.repository.cadastro.evento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.evolution.repository.cadastro.filter.EventoFilter;
import com.evolution.repository.cadastro.projection.EventoResumo;

public interface EventoRepositoryQuery {

	public Page<EventoResumo> resumir(EventoFilter eventoFilter, Pageable pageable);

}
