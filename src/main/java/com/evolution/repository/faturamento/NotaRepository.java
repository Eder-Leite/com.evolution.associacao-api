package com.evolution.repository.faturamento;

import java.math.BigDecimal;

import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.transaction.annotation.Transactional;

import com.evolution.model.faturamento.Nota;
import com.evolution.repository.faturamento.nota.NotaRepositoryQuery;

@NamedStoredProcedureQueries({
		@NamedStoredProcedureQuery(name = "FAT_PAUTENTICA_NOTA", procedureName = "FAT_PAUTENTICA_NOTA", parameters = {
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_EMPRESA", type = Long.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_NOTA", type = Long.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_TIPO", type = String.class),
				@StoredProcedureParameter(mode = ParameterMode.OUT, name = "P_MENSAGEM", type = String.class) }),

		@NamedStoredProcedureQuery(name = "FAT_PCANCELA_NOTA", procedureName = "FAT_PCANCELA_NOTA", parameters = {
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_EMPRESA", type = Long.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_NOTA", type = Long.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_TIPO", type = String.class),
				@StoredProcedureParameter(mode = ParameterMode.OUT, name = "P_MENSAGEM", type = String.class) }),

		@NamedStoredProcedureQuery(name = "FAT_PFALHA_AUTENTICACAO_NOTA", procedureName = "FAT_PFALHA_AUTENTICACAO_NOTA", parameters = {
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_EMPRESA", type = Long.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_NOTA", type = Long.class) }),

		@NamedStoredProcedureQuery(name = "FAT_PINSERE_NOTA_DEPARTAMENTO", procedureName = "FAT_PINSERE_NOTA_DEPARTAMENTO", parameters = {
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_EMPRESA", type = Long.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_EVENTO", type = Long.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_TIPO_DESCONTO", type = Long.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_PERIODO", type = Long.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_PARCELA", type = Long.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_FUNCIONARIO", type = Long.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_USUARIO", type = Long.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_VALOR", type = BigDecimal.class),
				@StoredProcedureParameter(mode = ParameterMode.OUT, name = "P_MENSAGEM", type = String.class) }),

		@NamedStoredProcedureQuery(name = "FAT_PINSERE_NOTA_LANCHONETE", procedureName = "FAT_PINSERE_NOTA_LANCHONETE", parameters = {
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_EMPRESA", type = Long.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_FUNCIONARIO", type = Long.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_USUARIO", type = Long.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "P_VALOR", type = BigDecimal.class),
				@StoredProcedureParameter(mode = ParameterMode.OUT, name = "P_NOTA", type = Long.class) }) })

@Transactional
public interface NotaRepository extends JpaRepository<Nota, Long>, NotaRepositoryQuery {

	@Procedure(name = "FAT_PAUTENTICA_NOTA")
	public String FAT_PAUTENTICA_NOTA(Long P_EMPRESA, Long P_NOTA, String P_TIPO);

	@Procedure(name = "FAT_PCANCELA_NOTA")
	public String FAT_PCANCELA_NOTA(Long P_EMPRESA, Long P_NOTA, String P_TIPO);

	@Procedure(name = "FAT_PFALHA_AUTENTICACAO_NOTA")
	public void FAT_PFALHA_AUTENTICACAO_NOTA(Long P_EMPRESA, Long P_NOTA);

	@Procedure(name = "FAT_PINSERE_NOTA_DEPARTAMENTO")
	public String FAT_PINSERE_NOTA_DEPARTAMENTO(Long P_EMPRESA, Long P_EVENTO, Long P_TIPO_DESCONTO, Long P_PERIODO,
			Long P_PARCELA, Long P_FUNCIONARIO, Long P_USUARIO, BigDecimal P_VALOR);

	@Procedure(name = "FAT_PINSERE_NOTA_LANCHONETE")
	public Long FAT_PINSERE_NOTA_LANCHONETE(Long P_EMPRESA, Long P_FUNCIONARIO, Long P_USUARIO, BigDecimal P_VALOR);

}
