package com.evolution.repository.adm.medidaTanque;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.evolution.repository.adm.filter.MedidaTanqueFilter;
import com.evolution.repository.adm.projection.MedidaTanqueResumo;

public interface MedidaTanqueRepositoryQuery {

	public Page<MedidaTanqueResumo> resumir(MedidaTanqueFilter medidaTanqueFilter, Pageable pageable);

}
