package com.evolution.repository.cadastro;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.evolution.model.cadastro.Funcionario;
import com.evolution.model.cadastro.enumerador.SituacaoFuncionario;
import com.evolution.repository.cadastro.funcionario.FuncionarioRepositoryQuery;

@Transactional
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long>, FuncionarioRepositoryQuery {

	public Funcionario findByCracha(String cracha);

	public List<Funcionario> findByNomeFuncionarioIgnoreCaseContaining(String nome);

	public Funcionario findByCrachaAndSituacaoFuncionario(String cracha, SituacaoFuncionario situacaoFuncionario);

	@Query(value = "SELECT * FROM FAT_VARQUIVO_FUNCIONARIO A", nativeQuery = true)
	public List<String> arquivoFuncionario();

}
