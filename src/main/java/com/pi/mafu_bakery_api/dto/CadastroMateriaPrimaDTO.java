package com.pi.mafu_bakery_api.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Digits;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class CadastroMateriaPrimaDTO {

    @Column(nullable = false, length = 200)
    private String nome;
    @Column(nullable = false, length = 2000)
    private String descricao;
    @Column(nullable = false)
    @Digits(integer = 3, fraction = 2)
    private BigDecimal preco;
    @Column(nullable = false)
    @Digits(integer = 10, fraction = 3)
    private BigDecimal quantidadeEstoque;
    @Column(nullable = false)
    private String unidadeMedida;

}
