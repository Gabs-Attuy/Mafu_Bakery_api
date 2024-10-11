package com.pi.mafu_bakery_api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class AlterarProdutoReqDTO {

    private String nome;
    private String descricao;
    private BigDecimal preco;
    private String tamanho;
    private List<IngredienteDTO> ingredientes;
    private Double avaliacao;
    private String categoria;
    @JsonIgnore
    private MultipartFile imagemPrincipal;
    @JsonIgnore
    private List<MultipartFile> imagensNovas;
    private List<String> urlImagensExcluidas;

}
