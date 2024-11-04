package com.pi.mafu_bakery_api.repository;

import com.pi.mafu_bakery_api.dto.ProdutosPedidoDTO;
import com.pi.mafu_bakery_api.key.PedidoProdutoKey;
import com.pi.mafu_bakery_api.model.PedidoProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PedidoProdutoRepository extends JpaRepository<PedidoProduto, PedidoProdutoKey> {
    @Query("SELECT new com.pi.mafu_bakery_api.dto.ProdutosPedidoDTO(p.id, p.preco, pp.quantidade, pp.total) " +
            "FROM PedidoProduto pp " +
            "JOIN pp.id.produtoId p " +
            "WHERE pp.id.pedidoId.id = :pedidoId")
    List<ProdutosPedidoDTO> findProdutosByPedidoId(@Param("pedidoId") Long pedidoId);


}
