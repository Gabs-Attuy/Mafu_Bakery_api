package com.pi.mafu_bakery_api.model;

import com.pi.mafu_bakery_api.key.PedidoProdutoKey;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Digits;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Entity
@Component
public class PedidoProduto {

    @EmbeddedId
    private PedidoProdutoKey id;
    @Column(nullable = false)
    private Integer quantidade;
    @Column(nullable = false)
    @Digits(integer = 5, fraction = 2)
    private BigDecimal valorUnitario;
    @Column(nullable = false)
    @Digits(integer = 5, fraction = 2)
    private BigDecimal total;

}
