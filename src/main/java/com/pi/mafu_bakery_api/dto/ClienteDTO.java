package com.pi.mafu_bakery_api.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class ClienteDTO {

    @Column(nullable = false)
    private String nomeCompleto;
    @CPF
    @Column(nullable = false, unique = true)
    private String cpf;
    @Email
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private Date dataDeNascimento;
    @Column(nullable = false)
    private String genero;
    @Column(nullable = false)
    private String senha;

}
