package com.pi.mafu_bakery_api.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class AlteracaoClienteDTO {

    private String nomeCompleto;

    private Date dataDeNascimento;
    private String genero;
}
