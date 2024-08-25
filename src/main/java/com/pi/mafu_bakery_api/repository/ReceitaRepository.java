package com.pi.mafu_bakery_api.repository;

import com.pi.mafu_bakery_api.key.ReceitaKey;
import com.pi.mafu_bakery_api.model.Receita;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceitaRepository extends JpaRepository<Receita, ReceitaKey> {}
