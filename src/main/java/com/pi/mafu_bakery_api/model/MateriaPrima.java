package com.pi.mafu_bakery_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class MateriaPrima {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 200)
    private String nome;
    @Column(nullable = false, length = 2000)
    private String descricao;
    @Column(nullable = false)
    @Digits(integer = 5, fraction = 2)
    private BigDecimal preco;
    @Column(nullable = false)
    @Digits(integer = 5, fraction = 3)
    private BigDecimal quantidadeEstoque;
    private Boolean status;
}
