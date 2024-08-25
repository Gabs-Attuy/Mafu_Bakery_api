package com.pi.mafu_bakery_api.repository;

import com.pi.mafu_bakery_api.key.PedidoProdutoKey;
import com.pi.mafu_bakery_api.model.PedidoProduto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoProdutoRepository extends JpaRepository<PedidoProduto, PedidoProdutoKey> {}
