package com.evolution.repository.adm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.evolution.model.adm.SubCategoria;
import com.evolution.repository.adm.subCategoria.SubCategoriaRepositoryQuery;

@Transactional
public interface SubCategoriaRepository extends JpaRepository<SubCategoria, Long>, SubCategoriaRepositoryQuery {

}
