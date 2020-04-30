package com.evolution.service.adm;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.evolution.model.adm.MovimentacaoTanque;
import com.evolution.repository.adm.MovimentacaoTanqueRepository;

@Service
public class MovimentacaoTanqueService {

	@Autowired
	private MovimentacaoTanqueRepository movimentacaoTanqueRepository;

	public MovimentacaoTanque atualizar(Long id, MovimentacaoTanque movimentacaoTanque) {
		MovimentacaoTanque movimentacaoTanqueSalva = buscarUsuarioPeloId(id);

		BeanUtils.copyProperties(movimentacaoTanque, movimentacaoTanqueSalva, "id");
		return movimentacaoTanqueRepository.save(movimentacaoTanqueSalva);
	}

	public MovimentacaoTanque buscarUsuarioPeloId(Long id) {
		MovimentacaoTanque movimentacaoTanqueSalva = movimentacaoTanqueRepository.findOne(id);
		if (movimentacaoTanqueSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return movimentacaoTanqueSalva;

	}

}
