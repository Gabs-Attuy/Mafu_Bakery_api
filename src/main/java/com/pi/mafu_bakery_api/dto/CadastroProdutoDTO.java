package com.pi.mafu_bakery_api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pi.mafu_bakery_api.model.MateriaPrima;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Digits;
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
public class CadastroProdutoDTO {

    @Column(nullable = false, length = 200)
    private String nome;
    @Column(nullable = false, length = 2000)
    private String descricao;
    @Digits(integer = 3, fraction = 2)
    private BigDecimal preco;
    @Column(nullable = false)
    private String tamanho;
    @Column(nullable = false)
    List<IngredienteDTO> ingredientes;
    private String categoria;
    @JsonIgnore
    private MultipartFile imagemPrincipal;
    @JsonIgnore
    private List<MultipartFile> imagens;
    private Double avaliacao;
}
