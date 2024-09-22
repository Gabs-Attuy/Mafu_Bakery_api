package com.pi.mafu_bakery_api.dto;

import com.pi.mafu_bakery_api.model.Produto;
import com.pi.mafu_bakery_api.model.URLImagem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class AlterarProdutoResDTO {

    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Integer quantidadeEstoque;
    private String categoria;
    private String tamanho;
    private Double avaliacao;
    private String imagemPrincipal;
    private List<String> imagens;

    public AlterarProdutoResDTO(Produto produtoModel){
        this.nome = produtoModel.getNome();
        this.descricao = produtoModel.getDescricao();
        this.preco = produtoModel.getPreco();
        this.quantidadeEstoque= produtoModel.getQuantidadeEstoque();
        this.categoria = produtoModel.getCategoria();
        this.tamanho = produtoModel.getTamanho();
        this.avaliacao = produtoModel.getAvaliacao();
        List<URLImagem> urlImagensModels = produtoModel.getUrlImagemList();
        for (URLImagem urlImagensModel : produtoModel.getUrlImagemList()) {
            if (urlImagensModel.getPrincipal()) {
                this.imagemPrincipal = urlImagensModel.getUrl();
            }
        }
        this.imagens = urlImagensModels.stream()
                .filter(urlImagensModel -> !urlImagensModel.getPrincipal())
                .map(URLImagem::getUrl)
                .toList();    }
}
