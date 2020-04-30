package com.evolution.resource.seguranca;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.evolution.event.RecursoCriadoEvent;
import com.evolution.model.seguranca.Departamento;
import com.evolution.repository.seguranca.DepartamentoRepository;
import com.evolution.service.cadastro.DepartamentoService;

@RestController
@RequestMapping("/departamentos")
public class DepartamentoResource {

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private DepartamentoRepository departamentoRepository;

	@Autowired
	private DepartamentoService departamentoService;

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_RH') and #oauth2.hasScope('read')")
	public ResponseEntity<Departamento> buscarPeloId(@PathVariable Long id) {
		Departamento departamento = departamentoRepository.findOne(id);
		return departamento != null ? ResponseEntity.ok(departamento) : ResponseEntity.notFound().build();
	}

	@GetMapping
	@PreAuthorize("hasAnyAuthority('ROLE_RH') and #oauth2.hasScope('read')")
	public List<Departamento> lista(HttpServletRequest request) {
		return departamentoRepository.findAll(new Sort(Sort.Direction.ASC, "nome"));
	}

	@PostMapping
	@PreAuthorize("hasAnyAuthority('ROLE_RH') and #oauth2.hasScope('write')")
	public ResponseEntity<Departamento> criar(@Valid @RequestBody Departamento departamento,
			HttpServletResponse response) {
		Departamento departamentoSalva = departamentoRepository.save(departamento);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, departamentoSalva.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(departamentoSalva);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_RH') and #oauth2.hasScope('write')")
	public ResponseEntity<Departamento> atualizar(@PathVariable Long id,
			@Valid @RequestBody Departamento departamento) {
		Departamento departamentoSalva = departamentoService.atualizar(id, departamento);
		return ResponseEntity.ok(departamentoSalva);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAnyAuthority('ROLE_RH') and #oauth2.hasScope('write')")
	public void remover(@PathVariable Long id) {
		departamentoRepository.delete(id);
	}

}
