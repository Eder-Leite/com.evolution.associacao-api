package com.evolution.service.cadastro;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.evolution.model.cadastro.Periodo;
import com.evolution.repository.cadastro.PeriodoRepository;

@Service
public class PeriodoService {

	@Autowired
	private PeriodoRepository periodoRepository;

	public Periodo atualizar(Long id, Periodo periodo) {
		Periodo periodoSalva = buscarUsuarioPeloId(id);

		BeanUtils.copyProperties(periodo, periodoSalva, "id");
		return periodoRepository.save(periodoSalva);
	}

	public Periodo buscarUsuarioPeloId(Long id) {
		Periodo periodoSalva = periodoRepository.findOne(id);
		if (periodoSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return periodoSalva;

	}

}
