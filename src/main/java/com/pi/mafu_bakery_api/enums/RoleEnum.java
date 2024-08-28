package com.pi.mafu_bakery_api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RoleEnum {
    ADMINISTRADOR("Administrador"),
    ESTOQUISTA("Estoquista"),
    CLIENTE("Cliente");

    private String descricao;

}
