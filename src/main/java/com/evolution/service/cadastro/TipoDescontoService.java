package com.evolution.service.cadastro;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.evolution.model.cadastro.TipoDesconto;
import com.evolution.repository.cadastro.TipoDescontoRepository;

@Service
public class TipoDescontoService {

	@Autowired
	private TipoDescontoRepository tipoDescontoRepository;

	public TipoDesconto atualizar(Long id, TipoDesconto tipoDesconto) {
		TipoDesconto tipoDescontoSalva = buscarUsuarioPeloId(id);

		BeanUtils.copyProperties(tipoDesconto, tipoDescontoSalva, "id");
		return tipoDescontoRepository.save(tipoDescontoSalva);
	}

	public TipoDesconto buscarUsuarioPeloId(Long id) {
		TipoDesconto tipoDescontoSalva = tipoDescontoRepository.findOne(id);
		if (tipoDescontoSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return tipoDescontoSalva;

	}

}
