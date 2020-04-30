package com.evolution.service.cadastro;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.evolution.model.seguranca.Departamento;
import com.evolution.repository.seguranca.DepartamentoRepository;

@Service
public class DepartamentoService {

	@Autowired
	private DepartamentoRepository departamentoRepository;

	public Departamento atualizar(Long id, Departamento departamento) {
		Departamento departamentoSalva = buscarUsuarioPeloId(id);

		BeanUtils.copyProperties(departamento, departamentoSalva, "id");
		return departamentoRepository.save(departamentoSalva);
	}

	public Departamento buscarUsuarioPeloId(Long id) {
		Departamento departamentoSalva = departamentoRepository.findOne(id);
		if (departamentoSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return departamentoSalva;

	}

}
