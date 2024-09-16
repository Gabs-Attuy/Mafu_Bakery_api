package com.pi.mafu_bakery_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class ProdutoResumoDTO {

    private Long id;
    private String nome;
    private Integer quantidadeEstoque;
    private BigDecimal preco;
    private Boolean status;
}
