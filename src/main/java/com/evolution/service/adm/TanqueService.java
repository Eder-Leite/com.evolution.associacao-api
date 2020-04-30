package com.evolution.service.adm;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.evolution.model.adm.Tanque;
import com.evolution.repository.adm.TanqueRepository;

@Service
public class TanqueService {

	@Autowired
	private TanqueRepository tanqueRepository;

	public Tanque atualizar(Long id, Tanque tanque) {
		Tanque tanqueSalva = buscarUsuarioPeloId(id);

		BeanUtils.copyProperties(tanque, tanqueSalva, "id");
		return tanqueRepository.save(tanqueSalva);
	}

	public Tanque buscarUsuarioPeloId(Long id) {
		Tanque tanqueSalva = tanqueRepository.findOne(id);
		if (tanqueSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return tanqueSalva;

	}

}
