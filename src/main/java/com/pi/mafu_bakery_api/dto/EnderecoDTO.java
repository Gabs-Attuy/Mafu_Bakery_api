package com.pi.mafu_bakery_api.dto;

import com.pi.mafu_bakery_api.enums.TipoEndereco;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class EnderecoDTO {

    @Column(nullable = false)
    private String cep;
    @Column(nullable = false)
    private String rua;
    @Column(nullable = false)
    private String bairro;
    @Column(nullable = false)
    private String cidade;
    @Column(nullable = false)
    private String numero;
    private String complemento;
    @Column(nullable = false)
    private String uf;
    @Column(nullable = false)
    private TipoEndereco tipo;
    @Column(nullable = false)
    private Boolean principal;

}
