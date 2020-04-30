package com.evolution.service.adm;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.evolution.model.adm.SubCategoria;
import com.evolution.repository.adm.SubCategoriaRepository;

@Service
public class SubCategoriaService {

	@Autowired
	private SubCategoriaRepository subCategoriaRepository;

	public SubCategoria atualizar(Long id, SubCategoria subCategoria) {
		SubCategoria subCategoriaSalva = buscarUsuarioPeloId(id);

		BeanUtils.copyProperties(subCategoria, subCategoriaSalva, "id");
		return subCategoriaRepository.save(subCategoriaSalva);
	}

	public SubCategoria buscarUsuarioPeloId(Long id) {
		SubCategoria subCategoriaSalva = subCategoriaRepository.findOne(id);
		if (subCategoriaSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return subCategoriaSalva;

	}

}
