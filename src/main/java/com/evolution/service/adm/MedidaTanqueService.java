package com.evolution.service.adm;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.evolution.model.adm.MedidaTanque;
import com.evolution.repository.adm.MedidaTanqueRepository;

@Service
public class MedidaTanqueService {

	@Autowired
	private MedidaTanqueRepository medidaTanqueRepository;

	public MedidaTanque atualizar(Long id, MedidaTanque medidaTanque) {
		MedidaTanque medidaTanqueSalva = buscarUsuarioPeloId(id);

		BeanUtils.copyProperties(medidaTanque, medidaTanqueSalva, "id");
		return medidaTanqueRepository.save(medidaTanqueSalva);
	}

	public MedidaTanque buscarUsuarioPeloId(Long id) {
		MedidaTanque medidaTanqueSalva = medidaTanqueRepository.findOne(id);
		if (medidaTanqueSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return medidaTanqueSalva;

	}

}
