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
import com.evolution.model.cadastro.Filial;
import com.evolution.repository.cadastro.FilialRepository;
import com.evolution.repository.cadastro.filter.FilialFilter;
import com.evolution.repository.cadastro.projection.FilialResumo;
import com.evolution.service.cadastro.FilialService;

@RestController
@RequestMapping("/filiais")
public class FilialResource {

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private FilialRepository filialRepository;

	@Autowired
	private FilialService filialService;

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_RH') and #oauth2.hasScope('read')")
	public ResponseEntity<Filial> buscarPeloId(@PathVariable Long id) {
		Filial filial = filialRepository.findOne(id);
		return filial != null ? ResponseEntity.ok(filial) : ResponseEntity.notFound().build();
	}

	@GetMapping
	@PreAuthorize("hasAnyAuthority('ROLE_RH') and #oauth2.hasScope('read')")
	public List<Filial> lista(HttpServletRequest request) {
		return filialRepository.findAll(new Sort(Sort.Direction.ASC, "empresa", "nome"));
	}

	@GetMapping(params = "resumo")
	@PreAuthorize("hasAnyAuthority('ROLE_USER') and #oauth2.hasScope('read')")
	public Page<FilialResumo> resumo(FilialFilter filialFilter, Pageable pageable) {
		return filialRepository.resumir(filialFilter, pageable);
	}

	@PostMapping
	@PreAuthorize("hasAnyAuthority('ROLE_RH') and #oauth2.hasScope('write')")
	public ResponseEntity<Filial> criar(@Valid @RequestBody Filial filial, HttpServletResponse response) {
		Filial empresaSalva = filialRepository.save(filial);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, empresaSalva.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(empresaSalva);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_RH') and #oauth2.hasScope('write')")
	public ResponseEntity<Filial> atualizar(@PathVariable Long id, @Valid @RequestBody Filial filial) {
		Filial empresaSalva = filialService.atualizar(id, filial);
		return ResponseEntity.ok(empresaSalva);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAnyAuthority('ROLE_RH') and #oauth2.hasScope('write')")
	public void remover(@PathVariable Long id) {
		filialRepository.delete(id);
	}

}
