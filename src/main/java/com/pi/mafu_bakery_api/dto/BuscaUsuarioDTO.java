package com.pi.mafu_bakery_api.dto;

import com.pi.mafu_bakery_api.enums.RoleEnum;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BuscaUsuarioDTO {

    private String nome;
    @CPF
    private String cpf;
    @Email
    private String email;
    private RoleEnum permissao;

}
