package com.pi.mafu_bakery_api.service;

import com.pi.mafu_bakery_api.dto.BuscaProdutoEReceitaDTO;
import com.pi.mafu_bakery_api.dto.CadastroProdutoDTO;
import com.pi.mafu_bakery_api.dto.IngredienteDTO;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.pi.mafu_bakery_api.BlobsAzure.BlobStorageService;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

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

        if (imagens != null && !imagens.isEmpty()) {
            String imageUrl = blobStorageService.uploadImage(imagens);
            URLImagem urlImagensModel = new URLImagem();
            urlImagensModel.setUrl(imageUrl);
            urlImagensModel.setProdutoId(produtoModel);
            urlRepository.save(urlImagensModel);
        }

    }

    public ResponseEntity<Produto> cadastraProduto(CadastroProdutoDTO dto) throws Exception {

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

                for(MultipartFile imagem : dto.getImagens()){
                    uploadImage(imagem, produto);
                }

//                List<Receita> receitas = new ArrayList<>();
                for (IngredienteDTO ingredienteDTO : dto.getIngredientes()) {
                    MateriaPrima materiaPrima = materiaPrimaRepository.findById(ingredienteDTO.getId())
                            .orElseThrow(() -> new Exception("Matéria-Prima não encontrada com o ID: " + ingredienteDTO.getId()));

                    Receita receita = new Receita();
                    ReceitaKey receitaKey = new ReceitaKey();
                    receitaKey.setProduto_id(produto);
                    receitaKey.setMateriaPrima_id(materiaPrima);
                    receita.setId(receitaKey);
                    receita.setQuantidadeNecessaria(ingredienteDTO.getQuantidade());
//                    receitas.add(receita);
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

    public ResponseEntity<Map<String, Object>> listarProdutos(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<Produto> produtoPage = produtoRepository.findAll(pageable);
        List<ProdutoResumoDTO> produtos = produtoPage.stream()
                .map(produto -> new ProdutoResumoDTO(
                        produto.getId(),
                        produto.getNome(),
                        produto.getQuantidadeEstoque(),
                        produto.getPreco(),
                        produto.getStatus()
                ))
                .collect(Collectors.toList());
        Map<String, Object> response = new HashMap<>();
        response.put("produtos", produtos);
        response.put("totalPages", produtoPage.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<BuscaProdutoEReceitaDTO> buscarProdutoeReceita(Long id) throws NoSuchElementException {

        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Produto não encontrado!"));
        List<IngredienteDTO> ingredientes = receitaRepository.findIngredientesByProdutoId(id);

        BuscaProdutoEReceitaDTO dto = new BuscaProdutoEReceitaDTO();
        dto.setNome(produto.getNome());
        dto.setAvaliacao(produto.getAvaliacao());
        dto.setCategoria(produto.getCategoria());
        dto.setDescricao(produto.getDescricao());
        dto.setPreco(produto.getPreco());
        dto.setTamanho(produto.getTamanho());
        dto.setIngredientes(ingredientes);
        dto.setImagens(produto.getUrlImagemList());

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    public ResponseEntity<Map<String, Object>> buscarProdutoPorNome(String nome, int page, int size) {
        Pageable paginacao = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<Produto> produtoPagina = produtoRepository.buscaPorNome(nome, paginacao);
        if (produtoPagina.getContent().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<ProdutoResumoDTO> produtos = produtoPagina.stream()
                .map(produto -> new ProdutoResumoDTO(
                        produto.getId(),
                        produto.getNome(),
                        produto.getQuantidadeEstoque(),
                        produto.getPreco(),
                        produto.getStatus()
                ))
                .collect(Collectors.toList());
        Map<String, Object> response = new HashMap<>();
        response.put("produtos", produtos);
        response.put("totalPages", produtoPagina.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
