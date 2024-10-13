package com.pi.mafu_bakery_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class ClienteInfoDTO {

    private Long id;
    private String nomeCompleto;
    private String cpf;
    private Date dataDeNascimento;
    private String genero;
    private String email;

}
