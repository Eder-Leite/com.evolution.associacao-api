package com.evolution.service.faturamento;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.evolution.model.faturamento.Nota;
import com.evolution.repository.faturamento.NotaRepository;

@Service
public class NotaService {

	@Autowired
	private NotaRepository notaRepository;

	public Nota atualizar(Long id, Nota nota) {
		Nota notaSalva = buscarUsuarioPeloId(id);

		BeanUtils.copyProperties(nota, notaSalva, "id");
		return notaRepository.save(notaSalva);
	}

	public Nota buscarUsuarioPeloId(Long id) {
		Nota notaSalva = notaRepository.findOne(id);
		if (notaSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return notaSalva;

	}

}
