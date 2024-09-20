package com.pi.mafu_bakery_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Entity
public class Produto {
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
    private String tamanho;
    private Integer quantidadeEstoque = 0;
    private Boolean status = true;
    @Column(nullable = false)
    private String categoria;
    @OneToMany(mappedBy = "produtoId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<URLImagem> urlImagemList;
    @Column(nullable = false)
    private Double avaliacao;
}
