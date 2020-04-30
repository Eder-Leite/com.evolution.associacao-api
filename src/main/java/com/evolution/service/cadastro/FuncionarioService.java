package com.evolution.service.cadastro;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.evolution.model.cadastro.Funcionario;
import com.evolution.repository.cadastro.FuncionarioRepository;

@Service
public class FuncionarioService {

	@Autowired
	private FuncionarioRepository FuncionarioRepository;

	public Funcionario atualizar(Long id, Funcionario Funcionario) {
		Funcionario FuncionarioSalva = buscarUsuarioPeloId(id);

		BeanUtils.copyProperties(Funcionario, FuncionarioSalva, "id");
		return FuncionarioRepository.save(FuncionarioSalva);
	}

	public Funcionario buscarUsuarioPeloId(Long id) {
		Funcionario FuncionarioSalva = FuncionarioRepository.findOne(id);
		if (FuncionarioSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return FuncionarioSalva;

	}

}
