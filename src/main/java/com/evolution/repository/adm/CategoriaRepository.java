package com.evolution.repository.adm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.evolution.model.adm.Categoria;
import com.evolution.repository.adm.categoria.CategoriaRepositoryQuery;

@Transactional
public interface CategoriaRepository extends JpaRepository<Categoria, Long>, CategoriaRepositoryQuery {

}
