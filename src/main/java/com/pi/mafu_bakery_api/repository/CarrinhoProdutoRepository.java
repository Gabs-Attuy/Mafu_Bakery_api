package com.pi.mafu_bakery_api.repository;

import com.pi.mafu_bakery_api.key.CarrinhoProdutoKey;
import com.pi.mafu_bakery_api.model.CarrinhoProduto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarrinhoProdutoRepository extends JpaRepository<CarrinhoProduto, CarrinhoProdutoKey> {}
