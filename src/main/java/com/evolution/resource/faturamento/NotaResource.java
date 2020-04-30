package com.evolution.resource.faturamento;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import com.evolution.model.faturamento.Nota;
import com.evolution.model.faturamento.Nota_;
import com.evolution.repository.faturamento.NotaRepository;
import com.evolution.repository.faturamento.filter.ManipulaNota;
import com.evolution.repository.faturamento.filter.NotaFilter;
import com.evolution.repository.faturamento.projection.NotaResumo;
import com.evolution.service.RelatorioService;
import com.evolution.service.faturamento.NotaService;
import com.evolution.service.seguranca.NegocioExceptionService;

import net.sf.jasperreports.engine.JRException;

@RestController
@RequestMapping("/notas")
public class NotaResource {

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private NotaRepository notaRepository;

	@Autowired
	private NotaService notaService;

	@Autowired
	private RelatorioService relatorioService;

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_USER') and #oauth2.hasScope('read')")
	public ResponseEntity<Nota> buscarPeloId(@PathVariable Long id) {
		Nota nota = notaRepository.findOne(id);
		return nota != null ? ResponseEntity.ok(nota) : ResponseEntity.notFound().build();
	}

	@GetMapping
	@PreAuthorize("hasAnyAuthority('ROLE_USER') and #oauth2.hasScope('read')")
	public List<Nota> lista(HttpServletRequest request) {
		return notaRepository.findAll(new Sort(Sort.Direction.ASC, Nota_.id.toString()));
	}

	@GetMapping(params = "resumo")
	@PreAuthorize("hasAnyAuthority('ROLE_USER') and #oauth2.hasScope('read')")
	public Page<NotaResumo> resumir(NotaFilter notaFilter, Pageable pageable) {
		return notaRepository.resumir(notaFilter, pageable);
	}

	@PostMapping
	@PreAuthorize("hasAnyAuthority('ROLE_USER') and #oauth2.hasScope('write')")
	public ResponseEntity<Nota> criar(@Valid @RequestBody Nota nota, HttpServletResponse response) {
		Nota notaSalva = notaRepository.save(nota);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, notaSalva.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(notaSalva);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN') and #oauth2.hasScope('write')")
	public ResponseEntity<Nota> atualizar(@PathVariable Long id, @Valid @RequestBody Nota nota) {
		Nota notaSalva = notaService.atualizar(id, nota);
		return ResponseEntity.ok(notaSalva);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN') and #oauth2.hasScope('write')")
	public void remover(@PathVariable Long id) {
		notaRepository.delete(id);
	}

	@PostMapping("/geraNotaDepartamento")
	@PreAuthorize("hasAnyAuthority('ROLE_USER') and #oauth2.hasScope('write')")
	public ResponseEntity<ManipulaNota> geraNotaDepartamento(@Valid @RequestBody ManipulaNota n,
			HttpServletResponse response) throws SQLException {

		n.setMensagem(notaRepository.FAT_PINSERE_NOTA_DEPARTAMENTO(n.getEmpresa(), n.getEvento(), n.getTipoDesconto(),
				n.getPeriodo(), n.getParcela(), n.getFuncionario(), n.getUsuario(), n.getValor()));
		return ResponseEntity.status(HttpStatus.CREATED).body(n);
	}

	@PostMapping("/cancelaNotaDepartamento")
	@PreAuthorize("hasAnyAuthority('ROLE_USER') and #oauth2.hasScope('write')")
	public ResponseEntity<ManipulaNota> cancelaNotaDepartamento(@Valid @RequestBody ManipulaNota n,
			HttpServletResponse response) throws SQLException {

		if (!autenticacaoCancelamentoEmitente(n)) {
			throw new NegocioExceptionService("Atenção usuário/senha para cancelamento incorreto!");
		}

		n.setTipo("U");
		n.setSenha(null);
		n.setMensagem(notaRepository.FAT_PCANCELA_NOTA(n.getEmpresa(), n.getNota(), n.getTipo()));
		n.setTipo(null);
		return ResponseEntity.status(HttpStatus.CREATED).body(n);
	}

	@PostMapping("/geraNotaLanchonete")
	@PreAuthorize("hasAnyAuthority('ROLE_LANCH') and #oauth2.hasScope('write')")
	public ResponseEntity<ManipulaNota> geraNotaLanchonete(@Valid @RequestBody ManipulaNota n,
			HttpServletResponse response) throws SQLException {

		Long id = notaRepository.FAT_PINSERE_NOTA_LANCHONETE(n.getEmpresa(), n.getFuncionario(), n.getUsuario(),
				n.getValor());

		if (id != null) {
			publisher.publishEvent(new RecursoCriadoEvent(this, response, id));
			n.setMensagem("Nota inserida com sucesso, aguardando autenticação!");
			n.setNota(id);
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(n);
	}

	@PostMapping("/cancelaNotaLanchonete")
	@PreAuthorize("hasAnyAuthority('ROLE_LANCH') and #oauth2.hasScope('write')")
	public ResponseEntity<ManipulaNota> cancelaNotaLanchonete(@Valid @RequestBody ManipulaNota n,
			HttpServletResponse response) throws SQLException {

		if (!autenticacaoCancelamentoEmitente(n)) {
			throw new NegocioExceptionService("Atenção usuário/senha para cancelamento incorreto!");
		}

		n.setTipo("U");
		n.setSenha(null);
		n.setMensagem(notaRepository.FAT_PCANCELA_NOTA(n.getEmpresa(), n.getNota(), n.getTipo()));
		n.setTipo(null);
		return ResponseEntity.status(HttpStatus.CREATED).body(n);
	}

	@PostMapping("/autenticaNotaLanchonete")
	@PreAuthorize("hasAnyAuthority('ROLE_LANCH') and #oauth2.hasScope('write')")
	public ResponseEntity<ManipulaNota> autenticaNotaLanchonete(@Valid @RequestBody ManipulaNota n,
			HttpServletResponse response) throws SQLException {

		if (!autenticacaoFuncionario(n)) {
			notaRepository.FAT_PFALHA_AUTENTICACAO_NOTA(n.getEmpresa(), n.getNota());
		}

		n.setTipo("U");
		n.setSenha(null);
		n.setMensagem(notaRepository.FAT_PAUTENTICA_NOTA(n.getEmpresa(), n.getNota(), n.getTipo()));

		n.setTipo(null);
		return ResponseEntity.status(HttpStatus.CREATED).body(n);
	}

	@PostMapping("/autenticaNotaDepartamento")
	@PreAuthorize("hasAnyAuthority('ROLE_LANCH') and #oauth2.hasScope('write')")
	public ResponseEntity<ManipulaNota> autenticaNotaDepartamento(@Valid @RequestBody ManipulaNota n,
			HttpServletResponse response) throws SQLException {

		if (!autenticacaoFuncionario(n)) {
			notaRepository.FAT_PFALHA_AUTENTICACAO_NOTA(n.getEmpresa(), n.getNota());
		}

		n.setTipo("U");
		n.setSenha(null);
		n.setMensagem(notaRepository.FAT_PAUTENTICA_NOTA(n.getEmpresa(), n.getNota(), n.getTipo()));
		n.setTipo(null);
		return ResponseEntity.status(HttpStatus.CREATED).body(n);
	}

	@PostMapping("/PDF")
	@PreAuthorize("hasAnyAuthority('ROLE_USER') and #oauth2.hasScope('write')")
	public void impressaoNotas(@Valid @RequestBody Map<String, Object> parametros, HttpServletResponse response)
			throws JRException, SQLException, IOException, URISyntaxException {
		this.relatorioService.gerarRelatorioPDF(parametros, "notas", "notaAssociacao.jasper", response);
	}

	@PostMapping("/relatorioPDF")
	@PreAuthorize("hasAnyAuthority('ROLE_USER') and #oauth2.hasScope('write')")
	public void impressaoRelatorio(@Valid @RequestBody Map<String, Object> parametros, HttpServletResponse response)
			throws JRException, SQLException, IOException, URISyntaxException {
		this.relatorioService.gerarRelatorioPDF(parametros, "relatorioNotas", "relatorioNotas.jasper", response);
	}

	@PostMapping("/posicaoSinteticaPDF")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN') and #oauth2.hasScope('write')")
	public void impressaoPosicaoSintetica(@Valid @RequestBody Map<String, Object> parametros,
			HttpServletResponse response) throws JRException, SQLException, IOException, URISyntaxException {
		this.relatorioService.gerarRelatorioPDF(parametros, "posicaoSintetica", "posicaoSintetica.jasper", response);
	}

	public String encodaSenha(String senha) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(senha);
	}

	public boolean autenticacaoCancelamentoEmitente(ManipulaNota nota) {
		Nota usuario = notaRepository.findOne(nota.getNota());
		BCryptPasswordEncoder password = new BCryptPasswordEncoder();

		if (password.matches(nota.getSenha(), usuario.getUsuario().getSenha())) {
			return true;
		} else {
			return false;
		}
	}

	public boolean autenticacaoFuncionario(ManipulaNota nota) {
		Nota funcionario = notaRepository.findOne(nota.getNota());
		BCryptPasswordEncoder password = new BCryptPasswordEncoder();

		if (password.matches(nota.getSenha(), funcionario.getUsuario().getSenha())) {
			return true;
		} else {
			return false;
		}
	}

	public void imprimeAtributoseValoresPojo(Object object) {
		Class<?> classe = object.getClass();
		Field[] campos = classe.getDeclaredFields();

		String nomeAtributo = "";
		Object valorAtributo = null;
		for (Field campo : campos) {
			try {
				nomeAtributo = campo.getName();
				campo.setAccessible(true);
				valorAtributo = campo.get(object);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(nomeAtributo + ": " + valorAtributo);
		}
	}
}
