package com.evolution.resource.relatorio;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.evolution.service.RelatorioService;

import net.sf.jasperreports.engine.JRException;

@RestController
@RequestMapping("/usuarios/PDF")
public class UsuarioRelatorioResource {

	@Autowired
	private RelatorioService relatorioService;

	@PostMapping("/relatorioUsuario")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMINISTRADOR', 'ROLE_DESENVOLVEDOR') and #oauth2.hasScope('read')")
	public void relatorioUsuario(@Valid @RequestBody Map<String, Object> parametros, HttpServletResponse response)
			throws JRException, SQLException, IOException, URISyntaxException {

		this.relatorioService.gerarRelatorioPDF(parametros, "relatorioUsuario", "user.jasper", response);
	}

	@PostMapping
	public void relatorio(UsuarioPdf usuarioPdf, HttpServletResponse response)
			throws JRException, SQLException, IOException, URISyntaxException {

		Map<String, Object> parametros = new HashMap<>();

		parametros.put("id", usuarioPdf.id);
		parametros.put("descricao", usuarioPdf.descricao);

		this.relatorioService.gerarPDF(parametros, "relatorioUsuario", "user.jasper", response);
	}

	@GetMapping("/ativos")
	public void ativos(Map<String, Object> parametros, HttpServletResponse response)
			throws JRException, SQLException, IOException, URISyntaxException {

		this.relatorioService.gerarAtivos(response);
	}

	@GetMapping("/cadPro")
	public void cadPro(Map<String, Object> parametros, HttpServletResponse response)
			throws JRException, SQLException, IOException, URISyntaxException {

		this.relatorioService.gerarCadPro(response);
	}

	public static class UsuarioPdf {

		private Long id;
		private String descricao;

		public UsuarioPdf() {
			super();
		}

		public UsuarioPdf(Long id, String descricao) {
			super();
			this.id = id;
			this.descricao = descricao;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getDescricao() {
			return descricao;
		}

		public void setDescricao(String descricao) {
			this.descricao = descricao;
		}

	}

}
