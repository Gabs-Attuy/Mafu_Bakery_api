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
public class AlteracaoMateriaPrimaDTO {

    @Column(length = 200)
    private String nome;
    @Column(length = 2000)
    private String descricao;
    @Digits(integer = 5, fraction = 2)
    private BigDecimal preco;
    private String unidadeMedida;

}
