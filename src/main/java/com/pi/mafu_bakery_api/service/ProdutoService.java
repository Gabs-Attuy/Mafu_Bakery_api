package com.pi.mafu_bakery_api.service;

import com.pi.mafu_bakery_api.dto.CadastroProdutoDTO;
import com.pi.mafu_bakery_api.interfaces.IProdutoService;
import com.pi.mafu_bakery_api.model.Produto;
import com.pi.mafu_bakery_api.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.pi.mafu_bakery_api.BlobsAzure.BlobStorageService;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProdutoService implements IProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;
  
    private final BlobStorageService blobStorageService;

    public ProdutoService(BlobStorageService blobStorageService) {
        this.blobStorageService = blobStorageService;
    }
  
    public String uploadImage(MultipartFile image) throws Exception {
        // Upload da imagem para o Azure Blob Storage
        return blobStorageService.uploadImage(image);
    }

    public ResponseEntity<Produto> cadastraProduto(CadastroProdutoDTO dto) throws Exception {

        /*TODO: Solicitar como entrada uma lista de ingredientes(Matéria-Prima) e a quantidade de cada uma para
        *  para fazer a validação de quantidade inserida no estoque inicial VS quantidade de cada ingrediente no estoque
        *  EX: Inseri 10 coxinhas e seus ingredientes. Preciso verificar se os ingredientes selecionados sao o suficiente para
        *  produzir 10 coxinhas.//
        * */

        try {
            if (checaSeOsParametrosDeEntradaNaoSaoNulos(dto)) {
                Produto produto = new Produto();
                produto.setNome(dto.getNome());
                produto.setDescricao(dto.getDescricao());
                produto.setTamanho(dto.getTamanho());
                produto.setPreco(dto.getPreco());
            }
        } catch (Exception e) {
                throw new Exception(e);
        }

        return null;
    }

    private boolean checaSeOsParametrosDeEntradaNaoSaoNulos(CadastroProdutoDTO dto) {
        return dto == null || dto.getNome() == null || dto.getDescricao() == null ||
                dto.getTamanho() == null || dto.getPreco() == null || dto.getQuantidadeEstoque() == null;
    }
}
