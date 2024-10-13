package com.pi.mafu_bakery_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class ClienteBuscaDTO {

    private Long id;
    private String nomeCompleto;
    private String email;
    private String permissao;
    private Date dataDeNascimento;
    private String genero;
    private String cpf;

    public ClienteBuscaDTO(Long id, String nomeCompleto, String cpf, Date dataDeNascimento, String genero, String email) {
        this.id = id;
        this.nomeCompleto = nomeCompleto;
        this.cpf = cpf;
        this.genero = genero;
        this.dataDeNascimento = dataDeNascimento;
        this.email = email;

    }

}
