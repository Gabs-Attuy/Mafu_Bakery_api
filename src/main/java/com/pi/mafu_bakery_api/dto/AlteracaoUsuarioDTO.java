package com.pi.mafu_bakery_api.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class AlteracaoUsuarioDTO {

    private String senha;
    private String confirmaSenha;

}
