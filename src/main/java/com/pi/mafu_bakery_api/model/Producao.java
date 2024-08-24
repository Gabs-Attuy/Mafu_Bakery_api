package com.pi.mafu_bakery_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Producao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private int quantidadeProduzida;
    @Column(nullable = false)
    private Date dataProducao;
    @OneToOne
    @JoinColumn(name = "produto_id")
    private Produto produtoProduzido;
}
