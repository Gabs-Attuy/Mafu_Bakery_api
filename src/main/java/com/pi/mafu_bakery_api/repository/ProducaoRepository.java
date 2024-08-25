package com.pi.mafu_bakery_api.repository;

import com.pi.mafu_bakery_api.model.Producao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProducaoRepository extends JpaRepository<Producao, Long> {}
