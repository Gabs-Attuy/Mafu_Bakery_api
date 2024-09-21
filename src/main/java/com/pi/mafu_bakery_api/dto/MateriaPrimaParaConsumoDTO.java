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
public class MateriaPrimaParaConsumoDTO {

    @Column(nullable = false)
    private Long id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    @Digits(integer = 3, fraction = 3)
    private BigDecimal quantidadeEstoque;

}
