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
import com.evolution.model.adm.MovimentacaoTanque;
import com.evolution.repository.adm.MovimentacaoTanqueRepository;
import com.evolution.repository.adm.filter.MovimentacaoTanqueFilter;
import com.evolution.repository.adm.projection.MovimentacaoTanqueResumo;
import com.evolution.service.adm.MovimentacaoTanqueService;

@RestController
@RequestMapping("/movimentacaoTanques")
public class MovimentacaoTanqueResource {

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private MovimentacaoTanqueRepository repository;

	@Autowired
	private MovimentacaoTanqueService service;

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_POSTO') and #oauth2.hasScope('read')")
	public ResponseEntity<MovimentacaoTanque> buscarPeloId(@PathVariable Long id) {
		MovimentacaoTanque movimentacaoTanque = repository.findOne(id);
		return movimentacaoTanque != null ? ResponseEntity.ok(movimentacaoTanque) : ResponseEntity.notFound().build();
	}

	@GetMapping
	@PreAuthorize("hasAnyAuthority('ROLE_POSTO') and #oauth2.hasScope('read')")
	public List<MovimentacaoTanque> lista(HttpServletRequest request) {
		return repository.findAll(new Sort(Sort.Direction.ASC, "id"));
	}

	@GetMapping(params = "resumo")
	@PreAuthorize("hasAnyAuthority('ROLE_POSTO') and #oauth2.hasScope('read')")
	public Page<MovimentacaoTanqueResumo> resumir(MovimentacaoTanqueFilter filtro, Pageable pageable) {
		return repository.resumir(filtro, pageable);
	}

	@PostMapping
	@PreAuthorize("hasAnyAuthority('ROLE_POSTO') and #oauth2.hasScope('write')")
	public ResponseEntity<MovimentacaoTanque> criar(@Valid @RequestBody MovimentacaoTanque movimentacaoTanque,
			HttpServletResponse response) {
		MovimentacaoTanque salva = repository.save(movimentacaoTanque);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, salva.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(salva);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_POSTO') and #oauth2.hasScope('write')")
	public ResponseEntity<MovimentacaoTanque> atualizar(@PathVariable Long id,
			@Valid @RequestBody MovimentacaoTanque movimentacaoTanque) {
		MovimentacaoTanque salva = service.atualizar(id, movimentacaoTanque);
		return ResponseEntity.ok(salva);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAnyAuthority('ROLE_POSTO') and #oauth2.hasScope('write')")
	public void remover(@PathVariable Long id) {
		repository.delete(id);
	}

}
