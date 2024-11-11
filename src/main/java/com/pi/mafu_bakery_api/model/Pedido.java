package com.pi.mafu_bakery_api.model;

import com.pi.mafu_bakery_api.enums.FormaPagamentoEnum;
import com.pi.mafu_bakery_api.enums.StatusPedido;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreationTimestamp
    private LocalDateTime dataPedido;
    @UpdateTimestamp
    private LocalDateTime dataAtualizacao;
    @ManyToOne
    @JoinColumn(name = "fk_endereco_envio_id", nullable = false)
    private Endereco enderecoEnvio;
    @Column(nullable = false)
    private FormaPagamentoEnum formaPagamento;
    @Column(nullable = false)
    @Digits(integer = 3, fraction = 2)
    private BigDecimal frete;
    @Column(nullable = false)
    @Digits(integer = 5, fraction = 2)
    private BigDecimal totalPedido;
    @Column(nullable = false)
    @Digits(integer = 5, fraction = 2)
    private BigDecimal subtotal;
    @ManyToOne
    @JoinColumn(name = "fk_cliente_id", nullable = false)
    private Cliente clienteId;
    @Column(nullable = false)
    private StatusPedido statusPedido;

}