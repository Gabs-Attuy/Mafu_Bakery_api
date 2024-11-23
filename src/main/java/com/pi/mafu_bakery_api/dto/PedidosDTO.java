package com.pi.mafu_bakery_api.dto;

import com.pi.mafu_bakery_api.enums.StatusPedido;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class PedidosDTO {

    private LocalDateTime dataPedido;
    private Long id;
    private BigDecimal totalPedido;
    private StatusPedido statusPedido;

}
