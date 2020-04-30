package com.evolution.service.adm;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.evolution.model.adm.Categoria;
import com.evolution.repository.adm.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;

	public Categoria atualizar(Long id, Categoria categoria) {
		Categoria categoriaSalva = buscarUsuarioPeloId(id);

		BeanUtils.copyProperties(categoria, categoriaSalva, "id");
		return categoriaRepository.save(categoriaSalva);
	}

	public Categoria buscarUsuarioPeloId(Long id) {
		Categoria categoriaSalva = categoriaRepository.findOne(id);
		if (categoriaSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return categoriaSalva;

	}

}
