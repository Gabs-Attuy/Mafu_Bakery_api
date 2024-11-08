package com.pi.mafu_bakery_api.interfaces;

import com.pi.mafu_bakery_api.dto.CriacaoPedidoDTO;
import com.pi.mafu_bakery_api.dto.DetalhesPedidoDTO;
import com.pi.mafu_bakery_api.dto.ProdutosPedidoDTO;
import com.pi.mafu_bakery_api.model.Pedido;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IPedido {

    ResponseEntity<Pedido> realizarPedido(CriacaoPedidoDTO dto) throws Exception;

    void cadastraRelacionamentoPedidoEProduto(Pedido pedido, ProdutosPedidoDTO dto) throws Exception;

    ResponseEntity<List<DetalhesPedidoDTO>> listarPedidosCliente(Long clienteId);
}
