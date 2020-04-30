package com.evolution.service;

import java.lang.String;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evolution.service.seguranca.NegocioExceptionService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

@Service
public class RelatorioService {

	@Autowired
	private DataSource dataSource;

	private boolean relatorioGerado;

	public void gerarRelatorioPDF(Map<String, Object> parametros, String nomeRelatorio, String relatorio,
			HttpServletResponse response) throws JRException, SQLException, IOException, URISyntaxException {

		System.out.println(parametros);

		try {

			InputStream jasperStream = this.getClass().getClassLoader()
					.getResourceAsStream("relatorios/" + relatorio);

			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);

			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros,
					dataSource.getConnection());

			this.relatorioGerado = jasperPrint.getPages().size() > 0;

			if (this.relatorioGerado) {
				response.setContentType("application/pdf");

				response.setHeader("Content-Disposition", "inline; filename=" + nomeRelatorio + ".pdf");

				final OutputStream outStream = response.getOutputStream();
				JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
			} else {
				throw new NegocioExceptionService("A execução não retornou dados.");
			}
		} catch (Exception e) {
			throw new NegocioExceptionService("Erro ao gerar PDF: " + e.getMessage());
		}

	}

	public void gerarPDF(Map<String, Object> parametros, String nomeRelatorio, String relatorio,
			HttpServletResponse response) throws JRException, SQLException, IOException, URISyntaxException {

		System.out.println(parametros);

		try {

			JasperPrint impressao = null;

			Thread.currentThread().getContextClassLoader();
			String caminho = getClass().getClassLoader().getResource("static/relatorios/").getPath();

			System.out.println(caminho);

			impressao = JasperFillManager.fillReport(caminho + relatorio, parametros, dataSource.getConnection());

			final byte[] bytes = JasperExportManager.exportReportToPdf(impressao);
			final byte[] arquivo = bytes;

			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "inline; filename=" + nomeRelatorio + ".pdf");
			response.setContentLengthLong(arquivo.length);

			final OutputStream outStream = response.getOutputStream();

			outStream.write(arquivo, 0, arquivo.length);
			outStream.flush();
			outStream.close();

		} catch (Exception e) {
			throw new NegocioExceptionService("Erro ao gerar PDF: " + e.getMessage());
		}

	}

	public void gerarAtivos(HttpServletResponse response)
			throws JRException, SQLException, IOException, URISyntaxException {

		Thread.currentThread().getContextClassLoader();
		// String caminho =
		// getClass().getClassLoader().getResource("static/relatorios/").getPath();
		final byte[] bytes = Files
				.readAllBytes(Paths.get("/Workpace-Sts/evolution/target/classes/static/relatorios/ativos.zip"));
		final byte[] arquivo = bytes;

		response.setHeader("Content-Disposition", "attachment; filename=ativos.zip");
		response.setContentLengthLong(arquivo.length);

		final OutputStream outStream = response.getOutputStream();

		outStream.write(arquivo, 0, arquivo.length);
		outStream.flush();
		outStream.close();
	}

	public void gerarCadPro(HttpServletResponse response)
			throws JRException, SQLException, IOException, URISyntaxException {

		Thread.currentThread().getContextClassLoader();
		// String caminho =
		// getClass().getClassLoader().getResource("static/relatorios/").getPath();
		final byte[] bytes = Files
				.readAllBytes(Paths.get("/Workpace-Sts/evolution/target/classes/static/relatorios/pubcadastropro.zip"));
		final byte[] arquivo = bytes;

		response.setHeader("Content-Disposition", "attachment; filename=pubcadastropro.zip");
		response.setContentLengthLong(arquivo.length);

		final OutputStream outStream = response.getOutputStream();

		outStream.write(arquivo, 0, arquivo.length);
		outStream.flush();
		outStream.close();
	}
}
