package com.evolution.service.adm;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.evolution.model.adm.Grupo;
import com.evolution.repository.adm.GrupoRepository;

@Service
public class GrupoService {

	@Autowired
	private GrupoRepository grupoRepository;

	public Grupo atualizar(Long id, Grupo grupo) {
		Grupo grupoSalva = buscarUsuarioPeloId(id);

		BeanUtils.copyProperties(grupo, grupoSalva, "id");
		return grupoRepository.save(grupoSalva);
	}

	public Grupo buscarUsuarioPeloId(Long id) {
		Grupo grupoSalva = grupoRepository.findOne(id);
		if (grupoSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return grupoSalva;

	}

}
