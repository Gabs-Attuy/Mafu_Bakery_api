package com.pi.mafu_bakery_api.repository;

import com.pi.mafu_bakery_api.dto.PedidosDTO;
import com.pi.mafu_bakery_api.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    @Query("SELECT new com.pi.mafu_bakery_api.dto.PedidosDTO(p.dataPedido, p.id, p.totalPedido, p.statusPedido)" +
            "FROM Pedido p ORDER BY p.dataPedido DESC")
    List<PedidosDTO> listarPedidosPorData();
}
