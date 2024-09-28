package com.pi.mafu_bakery_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class ExibicaoProdutoDTO {

    private Long id;
    private String nome;
    private String descricao;
    private Double avaliacao;
    private BigDecimal preco;
    private List<String> imagens;
    private Boolean status;
}
