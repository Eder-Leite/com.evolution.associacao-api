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
import com.evolution.model.faturamento.ItemOrdemServico;
import com.evolution.repository.faturamento.ItemOrdemServicoRepository;
import com.evolution.repository.faturamento.filter.ItemOrdemServicoFilter;
import com.evolution.repository.faturamento.projection.ItemOrdemServicoResumo;
import com.evolution.service.faturamento.ItemOrdemServicoService;

@RestController
@RequestMapping("/itemOrdemServicos")
public class ItemOrdemServicoResource {

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private ItemOrdemServicoRepository repository;

	@Autowired
	private ItemOrdemServicoService service;

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_POSTO') and #oauth2.hasScope('read')")
	public ResponseEntity<ItemOrdemServico> buscarPeloId(@PathVariable Long id) {
		ItemOrdemServico itemOrdemServico = repository.findOne(id);
		return itemOrdemServico != null ? ResponseEntity.ok(itemOrdemServico) : ResponseEntity.notFound().build();
	}

	@GetMapping
	@PreAuthorize("hasAnyAuthority('ROLE_POSTO') and #oauth2.hasScope('read')")
	public List<ItemOrdemServico> lista(HttpServletRequest request) {
		return repository.findAll(new Sort(Sort.Direction.ASC, "id"));
	}

	@GetMapping(params = "resumo")
	@PreAuthorize("hasAnyAuthority('ROLE_POSTO') and #oauth2.hasScope('read')")
	public Page<ItemOrdemServicoResumo> resumir(ItemOrdemServicoFilter filtro, Pageable pageable) {
		return repository.resumir(filtro, pageable);
	}

	@PostMapping
	@PreAuthorize("hasAnyAuthority('ROLE_POSTO') and #oauth2.hasScope('write')")
	public ResponseEntity<ItemOrdemServico> criar(@Valid @RequestBody ItemOrdemServico itemOrdemServico,
			HttpServletResponse response) {
		ItemOrdemServico salva = repository.save(itemOrdemServico);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, salva.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(salva);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_POSTO') and #oauth2.hasScope('write')")
	public ResponseEntity<ItemOrdemServico> atualizar(@PathVariable Long id,
			@Valid @RequestBody ItemOrdemServico itemOrdemServico) {
		ItemOrdemServico salva = service.atualizar(id, itemOrdemServico);
		return ResponseEntity.ok(salva);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAnyAuthority('ROLE_POSTO') and #oauth2.hasScope('write')")
	public void remover(@PathVariable Long id) {
		repository.delete(id);
	}
}
