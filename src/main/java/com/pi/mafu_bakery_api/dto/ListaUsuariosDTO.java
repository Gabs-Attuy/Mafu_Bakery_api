package com.pi.mafu_bakery_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class ListaUsuariosDTO {

    private String nomeUsuario;
    private String emailUsuario;
    private Boolean statusUsuario;
    private String grupo;

}
