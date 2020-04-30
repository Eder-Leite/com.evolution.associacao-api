package com.evolution.service.cadastro;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.evolution.model.cadastro.Filial;
import com.evolution.repository.cadastro.FilialRepository;

@Service
public class FilialService {

	@Autowired
	private FilialRepository filialRepository;

	public Filial atualizar(Long id, Filial Filial) {
		Filial filialSalva = buscarUsuarioPeloId(id);

		BeanUtils.copyProperties(Filial, filialSalva, "id");
		return filialRepository.save(filialSalva);
	}

	public Filial buscarUsuarioPeloId(Long id) {
		Filial filialSalva = filialRepository.findOne(id);
		if (filialSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return filialSalva;

	}

}
