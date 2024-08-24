package com.pi.mafu_bakery_api.key;

import com.pi.mafu_bakery_api.model.Carrinho;
import com.pi.mafu_bakery_api.model.Produto;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Embeddable
@EqualsAndHashCode
public class CarrinhoProdutoKey {

    @ManyToOne
    @JoinColumn(name = "fk_carrinho_id")
    private Carrinho carrinho_id;
    @ManyToOne
    @JoinColumn(name = "fk_produto_id")
    private Produto produto_id;

}
