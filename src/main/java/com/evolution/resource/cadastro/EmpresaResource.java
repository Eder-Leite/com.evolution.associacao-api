package com.evolution.resource.cadastro;

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
import com.evolution.model.cadastro.Empresa;
import com.evolution.repository.cadastro.EmpresaRepository;
import com.evolution.repository.cadastro.filter.EmpresaFilter;
import com.evolution.repository.cadastro.projection.EmpresaResumo;
import com.evolution.service.cadastro.EmpresaService;

@RestController
@RequestMapping("/empresas")
public class EmpresaResource {

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private EmpresaRepository empresaRepository;

	@Autowired
	private EmpresaService empresaService;

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_RH') and #oauth2.hasScope('read')")
	public ResponseEntity<Empresa> buscarPeloId(@PathVariable Long id) {
		Empresa empresa = empresaRepository.findOne(id);
		return empresa != null ? ResponseEntity.ok(empresa) : ResponseEntity.notFound().build();
	}

	@GetMapping
	@PreAuthorize("hasAnyAuthority('ROLE_RH') and #oauth2.hasScope('read')")
	public List<Empresa> lista(HttpServletRequest request) {
		return empresaRepository.findAll(new Sort(Sort.Direction.ASC, "nomeEmpresa"));
	}

	@GetMapping(params = "resumo")
	@PreAuthorize("hasAnyAuthority('ROLE_RH') and #oauth2.hasScope('read')")
	public Page<EmpresaResumo> resumo(EmpresaFilter empresaFilter, Pageable pageable) {
		return empresaRepository.resumir(empresaFilter, pageable);
	}

	@PostMapping
	@PreAuthorize("hasAnyAuthority('ROLE_RH') and #oauth2.hasScope('write')")
	public ResponseEntity<Empresa> criar(@Valid @RequestBody Empresa empresa, HttpServletResponse response) {
		Empresa empresaSalva = empresaRepository.save(empresa);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, empresaSalva.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(empresaSalva);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_RH') and #oauth2.hasScope('write')")
	public ResponseEntity<Empresa> atualizar(@PathVariable Long id, @Valid @RequestBody Empresa empresa) {
		Empresa empresaSalva = empresaService.atualizar(id, empresa);
		return ResponseEntity.ok(empresaSalva);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAnyAuthority('ROLE_RH') and #oauth2.hasScope('write')")
	public void remover(@PathVariable Long id) {
		empresaRepository.delete(id);
	}

}
