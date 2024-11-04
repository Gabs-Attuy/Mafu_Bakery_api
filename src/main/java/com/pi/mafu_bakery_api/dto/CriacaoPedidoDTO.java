package com.pi.mafu_bakery_api.dto;

import com.pi.mafu_bakery_api.enums.FormaPagamentoEnum;
import com.pi.mafu_bakery_api.model.Cliente;
import com.pi.mafu_bakery_api.model.Endereco;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class CriacaoPedidoDTO {

    @NotEmpty(message = "Id do cliente obrigatório!")
    private Long clienteId;
    @NotEmpty(message = "Endereço de entrega obrigatório!")
    private Long enderecoEntrega;
    @NotEmpty(message = "Ao menos 1 produto é necessário!")
    private List<ProdutosPedidoDTO> produtos;
    @NotEmpty(message = "A forma de pagamento é obrigatória!")
    private FormaPagamentoEnum formaPagamento;
    @NotEmpty(message = "O total do pedido é obrigatório!")
    @Digits(integer = 5, fraction = 2)
    private BigDecimal totalPedido;
    @NotEmpty(message = "O subtotal do pedido é obrigatório!")
    @Digits(integer = 5, fraction = 2)
    private BigDecimal subtotal;
    @NotEmpty(message = "O valor de frete é obrigatório!")
    @Digits(integer = 3, fraction = 2)
    private BigDecimal frete;

}
