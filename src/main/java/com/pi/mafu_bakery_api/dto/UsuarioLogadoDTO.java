package com.pi.mafu_bakery_api.dto;

import com.pi.mafu_bakery_api.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class UsuarioLogadoDTO {

    private Long id;
    private String nome;
    private RoleEnum permissao;
}
