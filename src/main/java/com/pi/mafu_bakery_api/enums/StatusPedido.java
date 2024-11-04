package com.pi.mafu_bakery_api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusPedido {
    AGUARDANDO_PAGAMENTO("AGUARDANDO PAGAMENTO"),
    AGUARDANDO_ENTREGA("AGUARDANDO ENTREGA"),
    ENTREGUE("ENTREGUE");

    private String status;
}
