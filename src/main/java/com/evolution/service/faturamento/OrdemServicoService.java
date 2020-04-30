package com.evolution.service.faturamento;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.evolution.model.faturamento.OrdemServico;
import com.evolution.repository.faturamento.OrdemServicoRepository;

@Service
public class OrdemServicoService {

	@Autowired
	private OrdemServicoRepository ordemServicoRepository;

	public OrdemServico atualizar(Long id, OrdemServico ordemServico) {
		OrdemServico ordemServicoSalva = buscarUsuarioPeloId(id);

		BeanUtils.copyProperties(ordemServico, ordemServicoSalva, "id");
		return ordemServicoRepository.save(ordemServicoSalva);
	}

	public OrdemServico buscarUsuarioPeloId(Long id) {
		OrdemServico ordemServicoSalva = ordemServicoRepository.findOne(id);
		if (ordemServicoSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return ordemServicoSalva;

	}

}
