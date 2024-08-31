package com.pi.mafu_bakery_api.dto;

import com.pi.mafu_bakery_api.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AlteracaoDTO {
    private String nome;
    @CPF
    private String cpf;
    private RoleEnum permissao;
}
