package com.pi.mafu_bakery_api.dto;

import com.pi.mafu_bakery_api.model.MateriaPrima;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Digits;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class CadastroProdutoDTO {

    @Column(nullable = false, length = 200)
    private String nome;
    @Column(nullable = false, length = 2000)
    private String descricao;
    @Digits(integer = 10, fraction = 2)
    private BigDecimal preco;
    @Column(nullable = false)
    private String tamanho;
    @Column(nullable = false)
    private Integer quantidadeEstoque;
    @Column(nullable = false)
    List<MateriaPrima> ingredientes;
    private Integer qtdIngredientes;
    private String categoria;
}
