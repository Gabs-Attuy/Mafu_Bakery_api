package com.pi.mafu_bakery_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class TokenDTO {

    private String email;
    private Boolean autenticado;
    private Date dataCriacao;
    private Date dataExpiracao;
    private String tokenAcesso;
}
