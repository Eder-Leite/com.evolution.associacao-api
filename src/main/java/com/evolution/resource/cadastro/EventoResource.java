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
import com.evolution.model.cadastro.Evento;
import com.evolution.repository.cadastro.EventoRepository;
import com.evolution.repository.cadastro.filter.EventoFilter;
import com.evolution.repository.cadastro.projection.EventoResumo;
import com.evolution.service.cadastro.EventoService;

@RestController
@RequestMapping("/eventos")
public class EventoResource {

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private EventoRepository eventoRepository;

	@Autowired
	private EventoService eventoService;

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_USER') and #oauth2.hasScope('read')")
	public ResponseEntity<Evento> buscarPeloId(@PathVariable Long id) {
		Evento evento = eventoRepository.findOne(id);
		return evento != null ? ResponseEntity.ok(evento) : ResponseEntity.notFound().build();
	}

	@GetMapping
	@PreAuthorize("hasAnyAuthority('ROLE_USER') and #oauth2.hasScope('read')")
	public List<Evento> lista(HttpServletRequest request) {
		return eventoRepository.findAll(new Sort(Sort.Direction.ASC, "descricao"));
	}
	
	@GetMapping(params = "resumo")
	@PreAuthorize("hasAnyAuthority('ROLE_USER') and #oauth2.hasScope('read')")
	public Page<EventoResumo> resumo(EventoFilter eventoFilter, Pageable pageable) {
		return eventoRepository.resumir(eventoFilter, pageable);
	}

	@PostMapping
	@PreAuthorize("hasAnyAuthority('ROLE_RH') and #oauth2.hasScope('write')")
	public ResponseEntity<Evento> criar(@Valid @RequestBody Evento evento, HttpServletResponse response) {
		Evento eventoSalva = eventoRepository.save(evento);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, eventoSalva.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(eventoSalva);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_RH') and #oauth2.hasScope('write')")
	public ResponseEntity<Evento> atualizar(@PathVariable Long id, @Valid @RequestBody Evento evento) {
		Evento eventoSalva = eventoService.atualizar(id, evento);
		return ResponseEntity.ok(eventoSalva);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAnyAuthority('ROLE_RH') and #oauth2.hasScope('write')")
	public void remover(@PathVariable Long id) {
		eventoRepository.delete(id);
	}

}
