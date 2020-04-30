package com.evolution.resource.receber;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.evolution.event.RecursoCriadoEvent;
import com.evolution.model.receber.ProcessoFolha;
import com.evolution.model.receber.ProcessoFolha_;
import com.evolution.repository.receber.ProcessoFolhaRepository;
import com.evolution.service.receber.ProcessoFolhaService;

@RestController
@RequestMapping("/processosFolha")
public class ProcessoFolhaResource {

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private ProcessoFolhaRepository processoFolhaRepository;

	@Autowired
	private ProcessoFolhaService processoFolhaService;

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN') and #oauth2.hasScope('read')")
	public ResponseEntity<ProcessoFolha> buscarPeloId(@PathVariable Long id) {
		ProcessoFolha processoFolha = processoFolhaRepository.findOne(id);
		return processoFolha != null ? ResponseEntity.ok(processoFolha) : ResponseEntity.notFound().build();
	}

	@GetMapping
	@ResponseBody
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN') and #oauth2.hasScope('read')")
	public List<ProcessoFolha> lista(HttpServletRequest request) {
		return processoFolhaRepository.findAll(new Sort(Sort.Direction.ASC, ProcessoFolha_.id.toString()));
	}

	@PostMapping
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN') and #oauth2.hasScope('write')")
	public ResponseEntity<ProcessoFolha> criar(@Valid @RequestBody ProcessoFolha processoFolha,
			HttpServletResponse response) {
		ProcessoFolha processoFolhaSalva = processoFolhaRepository.save(processoFolha);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, processoFolhaSalva.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(processoFolhaSalva);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN') and #oauth2.hasScope('write')")
	public ResponseEntity<ProcessoFolha> atualizar(@PathVariable Long id,
			@Valid @RequestBody ProcessoFolha processoFolha) {
		ProcessoFolha processoFolhaSalva = processoFolhaService.atualizar(id, processoFolha);
		return ResponseEntity.ok(processoFolhaSalva);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN') and #oauth2.hasScope('write')")
	public void remover(@PathVariable Long id) {
		processoFolhaRepository.delete(id);
	}

}
