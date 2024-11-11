package com.pi.mafu_bakery_api.dto;

import com.pi.mafu_bakery_api.model.Pedido;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class DetalhesPedidoDTO {

    private Pedido pedido;
    private List<ProdutosPedidoDTO> produtos;
}
