package com.evolution.service.receber;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.evolution.model.receber.ProcessoFolha;
import com.evolution.repository.receber.ProcessoFolhaRepository;

@Service
public class ProcessoFolhaService {

	@Autowired
	private ProcessoFolhaRepository processoFolhaRepository;

	public ProcessoFolha atualizar(Long id, ProcessoFolha processoFolha) {
		ProcessoFolha processoFolhaSalva = buscarUsuarioPeloId(id);

		BeanUtils.copyProperties(processoFolha, processoFolhaSalva, "id");
		return processoFolhaRepository.save(processoFolhaSalva);
	}

	public ProcessoFolha buscarUsuarioPeloId(Long id) {
		ProcessoFolha processoFolhaSalva = processoFolhaRepository.findOne(id);
		if (processoFolhaSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return processoFolhaSalva;

	}

}
