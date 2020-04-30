package com.evolution.service.faturamento;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.evolution.model.faturamento.ItemOrdemServico;
import com.evolution.repository.faturamento.ItemOrdemServicoRepository;

@Service
public class ItemOrdemServicoService {

	@Autowired
	private ItemOrdemServicoRepository itemOrdemServicoRepository;

	public ItemOrdemServico atualizar(Long id, ItemOrdemServico itemOrdemServico) {
		ItemOrdemServico itemOrdemServicoSalva = buscarUsuarioPeloId(id);

		BeanUtils.copyProperties(itemOrdemServico, itemOrdemServicoSalva, "id");
		return itemOrdemServicoRepository.save(itemOrdemServicoSalva);
	}

	public ItemOrdemServico buscarUsuarioPeloId(Long id) {
		ItemOrdemServico itemOrdemServicoSalva = itemOrdemServicoRepository.findOne(id);
		if (itemOrdemServicoSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return itemOrdemServicoSalva;

	}

}
