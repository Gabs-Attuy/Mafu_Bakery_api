package com.pi.mafu_bakery_api.dto;

import com.pi.mafu_bakery_api.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class ListaUsuariosDTO {

    private String nome;
    private String email;
    private Boolean isEnabled;
    private RoleEnum permissao;

}
