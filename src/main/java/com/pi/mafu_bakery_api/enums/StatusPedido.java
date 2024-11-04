package com.pi.mafu_bakery_api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusPedido {
    EM_ANDAMENTO("EM ANDAMENTO"),
    EM_ROTA("EM ROTA"),
    ENTREGUE("ENTREGUE");

    private String status;
}
