package com.evolution.resource.adm;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.evolution.event.RecursoCriadoEvent;
import com.evolution.model.adm.Produto;
import com.evolution.repository.adm.ProdutoRepository;
import com.evolution.repository.adm.filter.ProdutoFilter;
import com.evolution.repository.adm.projection.ProdutoResumo;
import com.evolution.service.adm.ProdutoService;

@RestController
@RequestMapping("/produtos")
public class ProdutoResource {

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private ProdutoRepository repository;

	@Autowired
	private ProdutoService service;

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_POSTO') and #oauth2.hasScope('read')")
	public ResponseEntity<Produto> buscarPeloId(@PathVariable Long id) {
		Produto produto = repository.findOne(id);
		return produto != null ? ResponseEntity.ok(produto) : ResponseEntity.notFound().build();
	}

	@GetMapping
	@PreAuthorize("hasAnyAuthority('ROLE_POSTO') and #oauth2.hasScope('read')")
	public List<Produto> lista(HttpServletRequest request) {
		return repository.findAll(new Sort(Sort.Direction.ASC, "id"));
	}

	@GetMapping(params = "resumo")
	@PreAuthorize("hasAnyAuthority('ROLE_POSTO') and #oauth2.hasScope('read')")
	public Page<ProdutoResumo> resumir(ProdutoFilter filtro, Pageable pageable) {
		return repository.resumir(filtro, pageable);
	}

	@PostMapping
	@PreAuthorize("hasAnyAuthority('ROLE_POSTO') and #oauth2.hasScope('write')")
	public ResponseEntity<Produto> criar(@Valid @RequestBody Produto produto,
			HttpServletResponse response) {
		Produto salva = repository.save(produto);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, salva.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(salva);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_POSTO') and #oauth2.hasScope('write')")
	public ResponseEntity<Produto> atualizar(@PathVariable Long id,
			@Valid @RequestBody Produto produto) {
		Produto salva = service.atualizar(id, produto);
		return ResponseEntity.ok(salva);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAnyAuthority('ROLE_POSTO') and #oauth2.hasScope('write')")
	public void remover(@PathVariable Long id) {
		repository.delete(id);
	}

}
