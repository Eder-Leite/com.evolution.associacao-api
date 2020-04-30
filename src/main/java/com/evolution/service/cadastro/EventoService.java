package com.evolution.service.cadastro;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.evolution.model.cadastro.Evento;
import com.evolution.repository.cadastro.EventoRepository;

@Service
public class EventoService {

	@Autowired
	private EventoRepository eventoRepository;

	public Evento atualizar(Long id, Evento evento) {
		Evento eventoSalva = buscarUsuarioPeloId(id);

		BeanUtils.copyProperties(evento, eventoSalva, "id");
		return eventoRepository.save(eventoSalva);
	}

	public Evento buscarUsuarioPeloId(Long id) {
		Evento eventoSalva = eventoRepository.findOne(id);
		if (eventoSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return eventoSalva;

	}

}
