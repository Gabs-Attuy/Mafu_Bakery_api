package com.pi.mafu_bakery_api.repository;

import com.pi.mafu_bakery_api.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {}
