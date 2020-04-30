package com.evolution.resource.faturamento;

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
import com.evolution.model.faturamento.OrdemServico;
import com.evolution.repository.faturamento.OrdemServicoRepository;
import com.evolution.repository.faturamento.filter.OrdemServicoFilter;
import com.evolution.repository.faturamento.projection.OrdemServicoResumo;
import com.evolution.service.faturamento.OrdemServicoService;

@RestController
@RequestMapping("/ordemServicos")
public class OrdemServicoResource {

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private OrdemServicoRepository repository;

	@Autowired
	private OrdemServicoService service;

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_POSTO') and #oauth2.hasScope('read')")
	public ResponseEntity<OrdemServico> buscarPeloId(@PathVariable Long id) {
		OrdemServico ordemServico = repository.findOne(id);
		return ordemServico != null ? ResponseEntity.ok(ordemServico) : ResponseEntity.notFound().build();
	}

	@GetMapping
	@PreAuthorize("hasAnyAuthority('ROLE_POSTO') and #oauth2.hasScope('read')")
	public List<OrdemServico> lista(HttpServletRequest request) {
		return repository.findAll(new Sort(Sort.Direction.ASC, "id"));
	}

	@GetMapping(params = "resumo")
	@PreAuthorize("hasAnyAuthority('ROLE_POSTO') and #oauth2.hasScope('read')")
	public Page<OrdemServicoResumo> resumir(OrdemServicoFilter filtro, Pageable pageable) {
		return repository.resumir(filtro, pageable);
	}

	@PostMapping
	@PreAuthorize("hasAnyAuthority('ROLE_POSTO') and #oauth2.hasScope('write')")
	public ResponseEntity<OrdemServico> criar(@Valid @RequestBody OrdemServico ordemServico,
			HttpServletResponse response) {
		OrdemServico salva = repository.save(ordemServico);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, salva.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(salva);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_POSTO') and #oauth2.hasScope('write')")
	public ResponseEntity<OrdemServico> atualizar(@PathVariable Long id,
			@Valid @RequestBody OrdemServico ordemServico) {
		OrdemServico salva = service.atualizar(id, ordemServico);
		return ResponseEntity.ok(salva);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAnyAuthority('ROLE_POSTO') and #oauth2.hasScope('write')")
	public void remover(@PathVariable Long id) {
		repository.delete(id);
	}
}
