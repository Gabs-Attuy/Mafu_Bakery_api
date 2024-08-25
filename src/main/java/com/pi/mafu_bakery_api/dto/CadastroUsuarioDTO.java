package com.pi.mafu_bakery_api.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class CadastroUsuarioDTO {

    @Column(nullable = false)
    private String nome;
    @CPF
    @Column(nullable = false)
    private String cpf;
    @Email
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String senha;
}
