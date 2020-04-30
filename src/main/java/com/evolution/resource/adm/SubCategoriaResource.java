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
import com.evolution.model.adm.SubCategoria;
import com.evolution.repository.adm.SubCategoriaRepository;
import com.evolution.repository.adm.filter.SubCategoriaFilter;
import com.evolution.repository.adm.projection.SubCategoriaResumo;
import com.evolution.service.adm.SubCategoriaService;

@RestController
@RequestMapping("/subCategorias")
public class SubCategoriaResource {

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private SubCategoriaRepository repository;

	@Autowired
	private SubCategoriaService service;

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_POSTO') and #oauth2.hasScope('read')")
	public ResponseEntity<SubCategoria> buscarPeloId(@PathVariable Long id) {
		SubCategoria subCategoria = repository.findOne(id);
		return subCategoria != null ? ResponseEntity.ok(subCategoria) : ResponseEntity.notFound().build();
	}

	@GetMapping
	@PreAuthorize("hasAnyAuthority('ROLE_POSTO') and #oauth2.hasScope('read')")
	public List<SubCategoria> lista(HttpServletRequest request) {
		return repository.findAll(new Sort(Sort.Direction.ASC, "id"));
	}

	@GetMapping(params = "resumo")
	@PreAuthorize("hasAnyAuthority('ROLE_POSTO') and #oauth2.hasScope('read')")
	public Page<SubCategoriaResumo> resumir(SubCategoriaFilter filtro, Pageable pageable) {
		return repository.resumir(filtro, pageable);
	}

	@PostMapping
	@PreAuthorize("hasAnyAuthority('ROLE_POSTO') and #oauth2.hasScope('write')")
	public ResponseEntity<SubCategoria> criar(@Valid @RequestBody SubCategoria subCategoria,
			HttpServletResponse response) {
		SubCategoria salva = repository.save(subCategoria);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, salva.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(salva);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_POSTO') and #oauth2.hasScope('write')")
	public ResponseEntity<SubCategoria> atualizar(@PathVariable Long id,
			@Valid @RequestBody SubCategoria subCategoria) {
		SubCategoria salva = service.atualizar(id, subCategoria);
		return ResponseEntity.ok(salva);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAnyAuthority('ROLE_POSTO') and #oauth2.hasScope('write')")
	public void remover(@PathVariable Long id) {
		repository.delete(id);
	}

}
