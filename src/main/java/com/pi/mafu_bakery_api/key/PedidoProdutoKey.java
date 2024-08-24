package com.pi.mafu_bakery_api.key;

import com.pi.mafu_bakery_api.model.Pedido;
import com.pi.mafu_bakery_api.model.Produto;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@EqualsAndHashCode
@Embeddable
public class PedidoProdutoKey {

    @ManyToOne
    @JoinColumn(name = "fk_pedido_id")
    private Pedido pedidoId;
    @ManyToOne
    @JoinColumn(name = "fk_produto_id")
    private Produto produtoId;

}
