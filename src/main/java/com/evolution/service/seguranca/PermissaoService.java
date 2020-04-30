package com.evolution.service.seguranca;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.evolution.model.seguranca.Permissao;
import com.evolution.repository.seguranca.PermissaoRepository;

@Service
public class PermissaoService {

	@Autowired
	private PermissaoRepository permissaoRepository;

	public Permissao atualizarUsuario(Long id, Permissao permissao) {
		Permissao permissaoSalva = buscarPeloId(id);

		BeanUtils.copyProperties(permissao, permissaoSalva, "id");
		return permissaoRepository.save(permissaoSalva);
	}

	public Permissao buscarPeloId(Long id) {
		Permissao permissaoSalva = permissaoRepository.findOne(id);
		if (permissaoSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return permissaoSalva;

	}
}
