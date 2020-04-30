package com.evolution.service.cadastro;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.evolution.model.cadastro.Empresa;
import com.evolution.repository.cadastro.EmpresaRepository;

@Service
public class EmpresaService {

	@Autowired
	private EmpresaRepository empresaRepository;

	public Empresa atualizar(Long id, Empresa empresa) {
		Empresa empresaSalva = buscarUsuarioPeloId(id);

		BeanUtils.copyProperties(empresa, empresaSalva, "id");
		return empresaRepository.save(empresaSalva);
	}

	public Empresa buscarUsuarioPeloId(Long id) {
		Empresa empresaSalva = empresaRepository.findOne(id);
		if (empresaSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return empresaSalva;

	}

}
