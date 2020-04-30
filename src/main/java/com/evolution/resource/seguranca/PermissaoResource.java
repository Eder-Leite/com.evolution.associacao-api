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
import com.evolution.model.seguranca.Permissao;
import com.evolution.repository.seguranca.PermissaoRepository;
import com.evolution.service.seguranca.PermissaoService;

@RestController
@RequestMapping("/permissoes")
public class PermissaoResource {

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private PermissaoRepository permissaoRepository;

	@Autowired
	private PermissaoService permissaoService;

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN') and #oauth2.hasScope('read')")
	public ResponseEntity<Permissao> buscarPorId(@PathVariable Long id) {
		Permissao permissao = permissaoRepository.findOne(id);
		return permissao != null ? ResponseEntity.ok(permissao) : ResponseEntity.notFound().build();
	}

	@GetMapping
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN') and #oauth2.hasScope('read')")
	public List<Permissao> lista(HttpServletRequest request) {
		return permissaoRepository.findAll(new Sort(Sort.Direction.ASC, "sigla"));
	}

	@PostMapping
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN') and #oauth2.hasScope('write')")
	public ResponseEntity<Permissao> criar(@Valid @RequestBody Permissao permissao, HttpServletResponse response) {
		Permissao permissaoSalva = permissaoRepository.save(permissao);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, permissaoSalva.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(permissaoSalva);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN') and #oauth2.hasScope('write')")
	public ResponseEntity<Permissao> atualizar(@PathVariable Long id, @Valid @RequestBody Permissao permissao) {
		Permissao permissaoSalva = permissaoService.atualizarUsuario(id, permissao);
		return ResponseEntity.ok(permissaoSalva);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN') and #oauth2.hasScope('write')")
	public void remover(@PathVariable Long id) {
		permissaoRepository.delete(id);
	}

}
