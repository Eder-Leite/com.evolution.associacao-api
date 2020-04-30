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
import com.evolution.model.adm.Grupo;
import com.evolution.repository.adm.GrupoRepository;
import com.evolution.repository.adm.filter.GrupoFilter;
import com.evolution.repository.adm.projection.GrupoResumo;
import com.evolution.service.adm.GrupoService;

@RestController
@RequestMapping("/grupos")
public class GrupoResource {

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private GrupoRepository repository;

	@Autowired
	private GrupoService service;

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_POSTO') and #oauth2.hasScope('read')")
	public ResponseEntity<Grupo> buscarPeloId(@PathVariable Long id) {
		Grupo grupo = repository.findOne(id);
		return grupo != null ? ResponseEntity.ok(grupo) : ResponseEntity.notFound().build();
	}

	@GetMapping
	@PreAuthorize("hasAnyAuthority('ROLE_POSTO') and #oauth2.hasScope('read')")
	public List<Grupo> lista(HttpServletRequest request) {
		return repository.findAll(new Sort(Sort.Direction.ASC, "id"));
	}

	@GetMapping(params = "resumo")
	@PreAuthorize("hasAnyAuthority('ROLE_POSTO') and #oauth2.hasScope('read')")
	public Page<GrupoResumo> resumir(GrupoFilter filtro, Pageable pageable) {
		return repository.resumir(filtro, pageable);
	}

	@PostMapping
	@PreAuthorize("hasAnyAuthority('ROLE_POSTO') and #oauth2.hasScope('write')")
	public ResponseEntity<Grupo> criar(@Valid @RequestBody Grupo grupo,
			HttpServletResponse response) {
		Grupo salva = repository.save(grupo);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, salva.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(salva);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_POSTO') and #oauth2.hasScope('write')")
	public ResponseEntity<Grupo> atualizar(@PathVariable Long id,
			@Valid @RequestBody Grupo grupo) {
		Grupo salva = service.atualizar(id, grupo);
		return ResponseEntity.ok(salva);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAnyAuthority('ROLE_POSTO') and #oauth2.hasScope('write')")
	public void remover(@PathVariable Long id) {
		repository.delete(id);
	}

}
