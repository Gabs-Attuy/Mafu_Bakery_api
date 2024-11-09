package com.pi.mafu_bakery_api.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class ProdutosPedidoDTO {

    @NotEmpty(message = "ID do produto obrigatório!")
    private Long id;
    @NotEmpty(message = "Valor unitário obrigatório!")
    @Digits(integer = 5, fraction = 2)
    private BigDecimal valorUnitario;
    @NotEmpty(message = "Quantidade de produtos obrigatória!")
    private Integer quantidade;
    @NotEmpty(message = "Total do produto obrigatório!")
    @Digits(integer = 5, fraction = 2)
    private BigDecimal total;
    private String urlImagemPrincipal;

}
