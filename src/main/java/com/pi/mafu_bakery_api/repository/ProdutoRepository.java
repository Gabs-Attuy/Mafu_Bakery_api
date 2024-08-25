package com.pi.mafu_bakery_api.repository;

import com.pi.mafu_bakery_api.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {}
