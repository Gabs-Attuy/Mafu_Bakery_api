package com.pi.mafu_bakery_api.model;

import com.pi.mafu_bakery_api.enums.FormaPagamentoEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private LocalDateTime dataPedido;
    @Column(nullable = false)
    private String enderecoEnvio;
    @Column(nullable = false)
    private FormaPagamentoEnum formaPagamento;
    @Column(nullable = false)
    private Double totalPedido;
    @ManyToOne
    @JoinColumn(name = "fk_usuario_id")
    private Usuario usuarioId;
    @OneToMany(mappedBy = "id.pedidoId", cascade = CascadeType.ALL)
    private List<PedidoProduto> pedidoProduto;
}
