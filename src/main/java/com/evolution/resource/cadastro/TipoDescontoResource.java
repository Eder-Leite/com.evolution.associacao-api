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
import com.evolution.model.cadastro.TipoDesconto;
import com.evolution.model.seguranca.Departamento;
import com.evolution.repository.cadastro.TipoDescontoRepository;
import com.evolution.repository.cadastro.filter.TipoDescontoFilter;
import com.evolution.repository.cadastro.projection.TipoDescontoResumo;
import com.evolution.repository.seguranca.DepartamentoRepository;
import com.evolution.service.cadastro.TipoDescontoService;

@RestController
@RequestMapping("/tipoDescontos")
public class TipoDescontoResource {

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private DepartamentoRepository departamentoRepository;

	@Autowired
	private TipoDescontoRepository tipoDescontoRepository;

	@Autowired
	private TipoDescontoService tipoDescontoService;

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_USER') and #oauth2.hasScope('read')")
	public ResponseEntity<TipoDesconto> buscarPeloId(@PathVariable Long id) {
		TipoDesconto periodo = tipoDescontoRepository.findOne(id);
		return periodo != null ? ResponseEntity.ok(periodo) : ResponseEntity.notFound().build();
	}

	@GetMapping
	@PreAuthorize("hasAnyAuthority('ROLE_USER') and #oauth2.hasScope('read')")
	public List<TipoDesconto> lista(HttpServletRequest request) {
		return tipoDescontoRepository.findAll(new Sort(Sort.Direction.ASC, "descricao"));
	}

	@GetMapping(params = "pesquisa")
	@PreAuthorize("hasAnyAuthority('ROLE_USER') and #oauth2.hasScope('read')")
	public List<TipoDesconto> tipoDescontoPorDepartamento(HttpServletRequest request, Long departamento) {
		Departamento departamentoSalva = departamentoRepository.findOne(departamento);
		return tipoDescontoRepository.findByDepartamentoOrderByDescricaoAsc(departamentoSalva);
	}

	@GetMapping(params = "resumo")
	@PreAuthorize("hasAnyAuthority('ROLE_USER') and #oauth2.hasScope('read')")
	public Page<TipoDescontoResumo> resumo(TipoDescontoFilter tipoDescontoFilter, Pageable pageable) {
		return tipoDescontoRepository.resumir(tipoDescontoFilter, pageable);
	}

	@PostMapping
	@PreAuthorize("hasAnyAuthority('ROLE_DEPTO') and #oauth2.hasScope('write')")
	public ResponseEntity<TipoDesconto> criar(@Valid @RequestBody TipoDesconto tipoDesconto,
			HttpServletResponse response) {
		TipoDesconto tipoDescontoSalva = tipoDescontoRepository.save(tipoDesconto);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, tipoDescontoSalva.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(tipoDescontoSalva);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_DEPTO') and #oauth2.hasScope('write')")
	public ResponseEntity<TipoDesconto> atualizar(@PathVariable Long id,
			@Valid @RequestBody TipoDesconto tipoDesconto) {
		TipoDesconto tipoDescontoSalva = tipoDescontoService.atualizar(id, tipoDesconto);
		return ResponseEntity.ok(tipoDescontoSalva);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAnyAuthority('ROLE_DEPTO') and #oauth2.hasScope('write')")
	public void remover(@PathVariable Long id) {
		tipoDescontoRepository.delete(id);
	}

}
