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
import com.evolution.model.cadastro.Periodo;
import com.evolution.model.cadastro.enumerador.SituacaoPeriodo;
import com.evolution.repository.cadastro.PeriodoRepository;
import com.evolution.repository.cadastro.filter.PeriodoFilter;
import com.evolution.service.cadastro.PeriodoService;
import com.evolution.repository.cadastro.projection.PeriodoResumo;

@RestController
@RequestMapping("/periodos")
public class PeriodoResource {

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private PeriodoRepository periodoRepository;

	@Autowired
	private PeriodoService periodoService;

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_USER') and #oauth2.hasScope('read')")
	public ResponseEntity<Periodo> buscarPeloId(@PathVariable Long id) {
		Periodo periodo = periodoRepository.findOne(id);
		return periodo != null ? ResponseEntity.ok(periodo) : ResponseEntity.notFound().build();
	}

	@GetMapping
	@PreAuthorize("hasAnyAuthority('ROLE_USER') and #oauth2.hasScope('read')")
	public List<Periodo> lista(HttpServletRequest request) {
		return periodoRepository.findAll(new Sort(Sort.Direction.ASC, "descricao"));
	}

	@GetMapping("/periodosAbertos")
	@PreAuthorize("hasAnyAuthority('ROLE_USER') and #oauth2.hasScope('read')")
	public List<Periodo> periodosAtivos(HttpServletRequest request) {
		return periodoRepository.findBySituacaoPeriodoOrderByDataInicioAsc(SituacaoPeriodo.ABERTO);
	}

	@GetMapping(params = "resumo")
	@PreAuthorize("hasAnyAuthority('ROLE_USER') and #oauth2.hasScope('read')")
	public Page<PeriodoResumo> resumo(PeriodoFilter periodoFilter, Pageable pageable) {
		return periodoRepository.resumir(periodoFilter, pageable);
	}

	@PostMapping
	@PreAuthorize("hasAnyAuthority('ROLE_RH') and #oauth2.hasScope('write')")
	public ResponseEntity<Periodo> criar(@Valid @RequestBody Periodo periodo, HttpServletResponse response) {
		Periodo periodoSalva = periodoRepository.save(periodo);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, periodoSalva.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(periodoSalva);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_RH') and #oauth2.hasScope('write')")
	public ResponseEntity<Periodo> atualizar(@PathVariable Long id, @Valid @RequestBody Periodo periodo) {
		Periodo periodoSalva = periodoService.atualizar(id, periodo);
		return ResponseEntity.ok(periodoSalva);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAnyAuthority('ROLE_RH') and #oauth2.hasScope('write')")
	public void remover(@PathVariable Long id) {
		periodoRepository.delete(id);
	}

}
