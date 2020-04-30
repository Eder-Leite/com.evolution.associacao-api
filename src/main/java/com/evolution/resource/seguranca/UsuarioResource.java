package com.evolution.resource.seguranca;

import java.util.HashMap;
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

import com.evolution.model.seguranca.Usuario;
import com.evolution.repository.seguranca.UsuarioRepository;
import com.evolution.repository.seguranca.filter.PerfilUsuario;
import com.evolution.repository.seguranca.filter.RecuperaSenha;
import com.evolution.repository.seguranca.projection.UsuarioResumo;
import com.evolution.service.seguranca.NegocioExceptionService;
import com.evolution.service.seguranca.UsuarioService;
import com.evolution.email.EmailService;
import com.evolution.email.Mail;
import com.evolution.event.RecursoCriadoEvent;

@RestController
@RequestMapping("/usuarios")
public class UsuarioResource {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private ApplicationEventPublisher publisher;

	private String senha;

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN') and #oauth2.hasScope('read')")
	public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
		Usuario usuario = usuarioRepository.findOne(id);
		return usuario != null ? ResponseEntity.ok(usuario) : ResponseEntity.notFound().build();
	}

	@GetMapping
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN') and #oauth2.hasScope('read')")
	public List<Usuario> lista(HttpServletRequest request) {
		return usuarioRepository.findAll(new Sort(Sort.Direction.ASC, "nome"));
	}

	@PostMapping
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN') and #oauth2.hasScope('write')")
	public ResponseEntity<Usuario> criarUsuario(@Valid @RequestBody Usuario usuario, HttpServletResponse response) {
		usuario.setSenha(encodaSenha(usuario.getSenha()));
		Usuario usuarioSalva = usuarioRepository.save(usuario);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, usuarioSalva.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSalva);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN') and #oauth2.hasScope('write')")
	public ResponseEntity<Usuario> atualizarUsuario(@PathVariable Long id, @Valid @RequestBody Usuario usuario) {
		usuario.setSenha(encodaSenha(usuario.getSenha()));
		Usuario usuarioSalva = usuarioService.atualizarUsuario(id, usuario);
		return ResponseEntity.ok(usuarioSalva);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN') and #oauth2.hasScope('write')")
	public void removerUsuario(@PathVariable Long id) {
		usuarioRepository.delete(id);
	}

	@GetMapping(params = "resumo")
	@PreAuthorize("hasAnyAuthority('ROLE_USER') and #oauth2.hasScope('read')")
	public Page<UsuarioResumo> resumo(UsuarioResumo usuarioResumo, Pageable pageable) {
		return usuarioRepository.resumir(usuarioResumo, pageable);
	}

	@PostMapping("/alteraSenha")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER') and #oauth2.hasScope('write')")
	public ResponseEntity<PerfilUsuario> alteraSenha(@Valid @RequestBody PerfilUsuario p,
			HttpServletResponse response) {

		if (!autenticacaoSenha(p)) {
			throw new NegocioExceptionService("Atenção a senha digitada não confere com a senha antiga!");
		}

		Usuario usuarioSalva = usuarioRepository.findOne(p.getId());
		usuarioSalva.setSenha(p.getSenhaNova());
		usuarioSalva.setSenha(encodaSenha(usuarioSalva.getSenha()));
		usuarioService.atualizarUsuario(usuarioSalva.getId(), usuarioSalva);

		p.setSenhaAntiga(null);
		p.setSenhaNova(null);
		return ResponseEntity.status(HttpStatus.CREATED).body(p);
	}

	@GetMapping(params = "perfil")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER') and #oauth2.hasScope('read')")
	public ResponseEntity<PerfilUsuario> buscarPerfilUsuario(Long id) {
		Usuario usuario = usuarioRepository.findOne(id);
		PerfilUsuario perfil = new PerfilUsuario(usuario);
		perfil.setSenhaAntiga(null);
		perfil.setSenhaNova(null);
		return perfil != null ? ResponseEntity.ok(perfil) : ResponseEntity.notFound().build();
	}

	@PostMapping("/recuperaSenha")
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ResponseEntity<RecuperaSenha> enviarSenha(@Valid @RequestBody RecuperaSenha recuperaSenha) {
		try {
			Usuario usuario = usuarioRepository.findByEmail(recuperaSenha.getEmail().toLowerCase());

			if (usuario != null) {
				if (usuario.getNome().toLowerCase().equals(recuperaSenha.getNome().toLowerCase())) {
					String senha = geraSenha();
					Usuario usuarioSalva = usuario;
					usuarioSalva.setSenha(senha);
					usuarioSalva.setSenha(encodaSenha(usuarioSalva.getSenha()));
					usuarioService.atualizarUsuario(usuarioSalva.getId(), usuarioSalva);

					Mail mail = new Mail();
					mail.setFrom("Evolution Sistemas <nao-responda@evolutionsistemas.com.br>");
					mail.setTo(usuarioSalva.getNome() + " <" + usuarioSalva.getEmail() + ">");
					mail.setSubject("Recuperação de Senha");

					Map model = new HashMap();
					model.put("nome", usuarioSalva.getNome());
					model.put("email", usuarioSalva.getEmail());
					model.put("senha", senha);
					mail.setModel(model);

					emailService.sendSimpleMessage(mail, "email-template");

					recuperaSenha.setMensagem("e-mail enviado com sucesso!");

					return ResponseEntity.ok(recuperaSenha);

				} else {
					return ResponseEntity.notFound().build();
				}
			} else {
				return ResponseEntity.notFound().build();
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());

			return ResponseEntity.notFound().build();
		}
	}

	public boolean autenticacaoSenha(PerfilUsuario p) {
		Usuario usuario = usuarioRepository.findOne(p.getId());
		BCryptPasswordEncoder password = new BCryptPasswordEncoder();

		if (password.matches(p.getSenhaAntiga(), usuario.getSenha())) {
			return true;
		} else {
			return false;
		}
	}

	public String geraSenha() {

		String[] carct = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };

		senha = "";

		for (int x = 0; x < 8; x++) {
			int j = (int) (Math.random() * carct.length);
			senha += carct[j];
		}
		return this.senha;
	}

	public String encodaSenha(String senha) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(senha);
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}