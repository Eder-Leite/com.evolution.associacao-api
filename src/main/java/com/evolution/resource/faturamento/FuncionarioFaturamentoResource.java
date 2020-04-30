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
import com.evolution.model.faturamento.FuncionarioFaturamento;
import com.evolution.repository.faturamento.FuncionarioFaturamentoRepository;
import com.evolution.repository.faturamento.filter.FuncionarioFilter;
import com.evolution.repository.faturamento.projection.FuncionarioResumo;
import com.evolution.service.faturamento.FuncionarioFaturamentoService;

@RestController
@RequestMapping("/fatFuncionarios")
public class FuncionarioFaturamentoResource {

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private FuncionarioFaturamentoRepository repository;

	@Autowired
	private FuncionarioFaturamentoService service;

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_POSTO') and #oauth2.hasScope('read')")
	public ResponseEntity<FuncionarioFaturamento> buscarPeloId(@PathVariable Long id) {
		FuncionarioFaturamento funcionario = repository.findOne(id);
		return funcionario != null ? ResponseEntity.ok(funcionario) : ResponseEntity.notFound().build();
	}

	@GetMapping
	@PreAuthorize("hasAnyAuthority('ROLE_POSTO') and #oauth2.hasScope('read')")
	public List<FuncionarioFaturamento> lista(HttpServletRequest request) {
		return repository.findAll(new Sort(Sort.Direction.ASC, "id"));
	}

	@GetMapping(params = "resumo")
	@PreAuthorize("hasAnyAuthority('ROLE_POSTO') and #oauth2.hasScope('read')")
	public Page<FuncionarioResumo> resumir(FuncionarioFilter filtro, Pageable pageable) {
		return repository.resumir(filtro, pageable);
	}

	@PostMapping
	@PreAuthorize("hasAnyAuthority('ROLE_POSTO') and #oauth2.hasScope('write')")
	public ResponseEntity<FuncionarioFaturamento> criar(@Valid @RequestBody FuncionarioFaturamento funcionario,
			HttpServletResponse response) {
		FuncionarioFaturamento salva = repository.save(funcionario);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, salva.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(salva);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_POSTO') and #oauth2.hasScope('write')")
	public ResponseEntity<FuncionarioFaturamento> atualizar(@PathVariable Long id, @Valid @RequestBody FuncionarioFaturamento funcionario) {
		FuncionarioFaturamento salva = service.atualizar(id, funcionario);
		return ResponseEntity.ok(salva);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAnyAuthority('ROLE_POSTO') and #oauth2.hasScope('write')")
	public void remover(@PathVariable Long id) {
		repository.delete(id);
	}
}
