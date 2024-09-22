package com.pi.mafu_bakery_api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pi.mafu_bakery_api.model.URLImagem;
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
public class BuscaProdutoEReceitaDTO {

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
    private Integer quantidadeEstoque;
    private String categoria;
    private List<ExibicaoImagemDTO> imagens;
    private Double avaliacao;

}
