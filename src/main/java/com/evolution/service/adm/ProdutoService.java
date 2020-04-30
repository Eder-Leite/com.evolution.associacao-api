package com.evolution.service.adm;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.evolution.model.adm.Produto;
import com.evolution.repository.adm.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;

	public Produto atualizar(Long id, Produto produto) {
		Produto produtoSalva = buscarUsuarioPeloId(id);

		BeanUtils.copyProperties(produto, produtoSalva, "id");
		return produtoRepository.save(produtoSalva);
	}

	public Produto buscarUsuarioPeloId(Long id) {
		Produto produtoSalva = produtoRepository.findOne(id);
		if (produtoSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return produtoSalva;

	}

}
