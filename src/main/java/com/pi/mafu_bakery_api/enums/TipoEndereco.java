package com.pi.mafu_bakery_api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TipoEndereco {
    FATURAMENTO("FATURAMENTO"),
    ENTREGA("ENTREGA");

    private String tipo;
}
