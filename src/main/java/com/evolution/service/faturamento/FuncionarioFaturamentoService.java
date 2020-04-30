package com.evolution.service.faturamento;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.evolution.model.faturamento.FuncionarioFaturamento;
import com.evolution.repository.faturamento.FuncionarioFaturamentoRepository;

@Service
public class FuncionarioFaturamentoService {

	@Autowired
	private FuncionarioFaturamentoRepository funcionarioRepository;

	public FuncionarioFaturamento atualizar(Long id, FuncionarioFaturamento funcionario) {
		FuncionarioFaturamento funcionarioSalva = buscarUsuarioPeloId(id);

		BeanUtils.copyProperties(funcionario, funcionarioSalva, "id");
		return funcionarioRepository.save(funcionarioSalva);
	}

	public FuncionarioFaturamento buscarUsuarioPeloId(Long id) {
		FuncionarioFaturamento funcionarioSalva = funcionarioRepository.findOne(id);
		if (funcionarioSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return funcionarioSalva;

	}

}
