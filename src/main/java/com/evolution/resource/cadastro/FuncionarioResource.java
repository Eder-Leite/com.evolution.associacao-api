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
import com.evolution.model.cadastro.Funcionario;
import com.evolution.model.cadastro.enumerador.SituacaoFuncionario;
import com.evolution.repository.cadastro.FuncionarioRepository;
import com.evolution.repository.cadastro.filter.FuncionarioFilter;
import com.evolution.repository.cadastro.projection.FuncionarioResumo;
import com.evolution.service.cadastro.FuncionarioService;


@RestController
@RequestMapping("/funcionarios")
public class FuncionarioResource {

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Autowired
	private FuncionarioService funcionarioService;

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_USER') and #oauth2.hasScope('read')")
	public ResponseEntity<Funcionario> buscarPeloId(@PathVariable Long id) {
		Funcionario funcionario = funcionarioRepository.findOne(id);
		return funcionario != null ? ResponseEntity.ok(funcionario) : ResponseEntity.notFound().build();
	}

	@GetMapping
	@PreAuthorize("hasAnyAuthority('ROLE_USER') and #oauth2.hasScope('read')")
	public List<Funcionario> lista(HttpServletRequest request) {
		return funcionarioRepository.findAll(new Sort(Sort.Direction.ASC, "nomeFuncionario"));
	}

	@PostMapping
	@PreAuthorize("hasAnyAuthority('ROLE_RH') and #oauth2.hasScope('write')")
	public ResponseEntity<Funcionario> criar(@Valid @RequestBody Funcionario funcionario,
			HttpServletResponse response) {
		Funcionario funcionarioSalvo = funcionarioRepository.save(funcionario);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, funcionarioSalvo.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(funcionarioSalvo);
	}

	@GetMapping(params = "resumo")
	@PreAuthorize("hasAnyAuthority('ROLE_USER') and #oauth2.hasScope('read')")
	public Page<FuncionarioResumo> resumo(FuncionarioFilter funcionarioFilter, Pageable pageable) {
		return funcionarioRepository.resumir(funcionarioFilter, pageable);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_RH') and #oauth2.hasScope('write')")
	public ResponseEntity<Funcionario> atualizar(@PathVariable Long id, @Valid @RequestBody Funcionario funcionario) {
		Funcionario funcionarioSalva = funcionarioService.atualizar(id, funcionario);
		return ResponseEntity.ok(funcionarioSalva);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAnyAuthority('ROLE_RH') and #oauth2.hasScope('write')")
	public void remover(@PathVariable Long id) {
		funcionarioRepository.delete(id);
	}

	@GetMapping("/buscaPorCracha/{cracha}")
	@PreAuthorize("hasAnyAuthority('ROLE_USER') and #oauth2.hasScope('read')")
	public ResponseEntity<Funcionario> buscarPorCracha(@PathVariable String cracha) {
		Funcionario func = funcionarioRepository.findByCrachaAndSituacaoFuncionario(cracha, SituacaoFuncionario.ATIVO);
		return func != null ? ResponseEntity.ok(func) : ResponseEntity.notFound().build();
	}

	@GetMapping("/buscaPorNome/{nome}")
	@PreAuthorize("hasAnyAuthority('ROLE_USER') and #oauth2.hasScope('read')")
	public List<Funcionario> buscaPorNome(@PathVariable String nome) {
		return funcionarioRepository.findByNomeFuncionarioIgnoreCaseContaining(nome);
	}

}
