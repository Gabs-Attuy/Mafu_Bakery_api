package com.pi.mafu_bakery_api.service;

import com.pi.mafu_bakery_api.dto.CadastroProdutoDTO;
import com.pi.mafu_bakery_api.dto.IngredienteDTO;
import com.pi.mafu_bakery_api.dto.ListaMateriaPrimaDTO;
import com.pi.mafu_bakery_api.dto.ProdutoResumoDTO;
import com.pi.mafu_bakery_api.interfaces.IProdutoService;
import com.pi.mafu_bakery_api.key.ReceitaKey;
import com.pi.mafu_bakery_api.model.MateriaPrima;
import com.pi.mafu_bakery_api.model.Produto;
import com.pi.mafu_bakery_api.model.Receita;
import com.pi.mafu_bakery_api.model.URLImagem;
import com.pi.mafu_bakery_api.repository.MateriaPrimaRepository;
import com.pi.mafu_bakery_api.repository.ProdutoRepository;
import com.pi.mafu_bakery_api.repository.ReceitaRepository;
import com.pi.mafu_bakery_api.repository.URLRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.pi.mafu_bakery_api.BlobsAzure.BlobStorageService;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProdutoService implements IProdutoService {

    @Autowired
    private URLRepository urlRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private MateriaPrimaRepository materiaPrimaRepository;

    @Autowired
    private ReceitaRepository receitaRepository;
  
    private final BlobStorageService blobStorageService;

    public ProdutoService(BlobStorageService blobStorageService) {
        this.blobStorageService = blobStorageService;
    }
  
    public void uploadImage(MultipartFile imagens, Produto produtoModel) throws Exception {
//        // Upload da imagem para o Azure Blob Storage
//        List<String> imagemUrls = new ArrayList<>();
//        for(MultipartFile imagem : imagens){
//            String imagemUrl = blobStorageService.uploadImage(imagem);
//            imagemUrls.add(imagemUrl);
//        }
//        return imagemUrls;

        if (imagens != null && !imagens.isEmpty()) {
            String imageUrl = blobStorageService.uploadImage(imagens);
            URLImagem urlImagensModel = new URLImagem();
            urlImagensModel.setUrl(imageUrl);
            urlImagensModel.setProdutoId(produtoModel);
            urlRepository.save(urlImagensModel);
        }
    }

    public ResponseEntity<Produto> cadastraProduto(CadastroProdutoDTO dto) throws Exception {

        /*TODO: Solicitar como entrada uma lista de ingredientes(Matéria-Prima) e a quantidade de cada uma para
        *  para fazer a validação de quantidade inserida no estoque inicial VS quantidade de cada ingrediente no estoque
        *  EX: Inseri 10 coxinhas e seus ingredientes. Preciso verificar se os ingredientes selecionados sao o suficiente para
        *  produzir 10 coxinhas.//
        *   adicionar unidade de medida na materia prima
        * */

        try {
            if (!checaSeOsParametrosDeEntradaNaoSaoNulos(dto)) {
                Produto produto = new Produto();
                produto.setNome(dto.getNome());
                produto.setDescricao(dto.getDescricao());
                produto.setTamanho(dto.getTamanho());
                produto.setPreco(dto.getPreco());
                produto.setCategoria(dto.getCategoria());
                produto.setAvaliacao(dto.getAvaliacao());

                produtoRepository.save(produto);

//                if (dto.getImagens() != null && !dto.getImagens().isEmpty()) {
//                    List<String> imagemUrls = uploadImage(dto.getImagens());
//                    List<URLImagem> imagens = new ArrayList<>();
//                    URLImagem imagem = new URLImagem();;
//                    for (String imageUrl : imagemUrls) {
//                        imagem.setUrl(imageUrl);
//                        imagem.setProdutoId(produto); // Associa a imagem ao produto
//                        imagens.add(imagem);
//                    }
//                    produto.setUrlImagemList(imagens); // Adiciona as imagens ao produto
//                    urlRepository.save(imagem);
//                }

                for(MultipartFile imagem : dto.getImagens()){
                    uploadImage(imagem, produto);
                }

                List<Receita> receitas = new ArrayList<>();
                for (IngredienteDTO ingredienteDTO : dto.getIngredientes()) {
                    // Busca a Matéria-Prima pelo ID no banco de dados
                    MateriaPrima materiaPrima = materiaPrimaRepository.findById(ingredienteDTO.getId())
                            .orElseThrow(() -> new Exception("Matéria-Prima não encontrada com o ID: " + ingredienteDTO.getId()));

                    // Cria uma nova instância de Receita, que relaciona a Matéria-Prima com o Produto
                    Receita receita = new Receita();
                    ReceitaKey receitaKey = new ReceitaKey();
                    receitaKey.setProduto_id(produto);
                    receitaKey.setMateriaPrima_id(materiaPrima);
                    receita.setId(receitaKey);
                    receita.setQuantidadeNecessaria(ingredienteDTO.getQuantidade()); // Quantidade usada para o produto
                    receitas.add(receita);
                    receitaRepository.save(receita);
                }
            }
        } catch (Exception e) {
                throw new Exception(e);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    private boolean checaSeOsParametrosDeEntradaNaoSaoNulos(CadastroProdutoDTO dto) {
        return dto == null || dto.getNome() == null || dto.getDescricao() == null ||
                dto.getTamanho() == null || dto.getPreco() == null || dto.getImagens() == null
                || dto.getIngredientes() == null || dto.getCategoria() == null
                || dto.getAvaliacao() == null;
    }

    public ResponseEntity<List<ProdutoResumoDTO>> listarProdutos() {
        return new ResponseEntity<>(produtoRepository.listarProdutosDesc(), HttpStatus.OK);
    }
}
