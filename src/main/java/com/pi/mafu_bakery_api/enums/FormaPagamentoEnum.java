package com.pi.mafu_bakery_api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum FormaPagamentoEnum {
    CREDITO("CREDITO"),
    DEBITO("DEBITO"),
    PIX("PIX");

    private String status;
}
