package com.pi.mafu_bakery_api.service;

import com.pi.mafu_bakery_api.dto.*;
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
import jakarta.transaction.Transactional;
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

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.logging.Logger;

@Service
public class ProdutoService implements IProdutoService {

    private static final Logger logger = Logger.getLogger(ProdutoService.class.getName());

    @Autowired
    private URLRepository urlRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private MateriaPrimaRepository materiaPrimaRepository;

    @Autowired
    private ReceitaRepository receitaRepository;

    @Autowired
    private MateriaPrimaService materiaPrimaService;

    private final BlobStorageService blobStorageService;

    public ProdutoService(BlobStorageService blobStorageService) {
        this.blobStorageService = blobStorageService;
    }

    public void uploadImage(MultipartFile imagem, Produto produtoModel, boolean principal) throws Exception {

        if (imagem != null && !imagem.isEmpty()) {
            String imageUrl = blobStorageService.uploadImage(imagem);
            URLImagem urlImagensModel = new URLImagem();
            urlImagensModel.setUrl(imageUrl);
            urlImagensModel.setPrincipal(principal);
            urlImagensModel.setProdutoId(produtoModel);
            urlRepository.save(urlImagensModel);
        }else{
            throw new Exception("Imagem inválida ou vazia!"); // Lança exceção se não houver imagem
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

                if (dto.getImagemPrincipal() != null) {
                    uploadImage(dto.getImagemPrincipal(), produto, true);
                }else {
                    logger.warning("Imagem principal não foi adicionada");
                }

                // Salvando as imagens adicionais
                if (dto.getImagens() != null && !dto.getImagens().isEmpty()) {
                    for (MultipartFile imagem : dto.getImagens()) {
                        if (imagem != null && !imagem.isEmpty()) {
                            uploadImage(imagem, produto,false);
                        } else {
                            logger.warning("Imagens não fornecidas");
                        }
                    }
                } else {
                    logger.info("Nenhuma imagem adicional fornecida");
                }

                for (IngredienteDTO ingredienteDTO : dto.getIngredientes()) {
                    MateriaPrima materiaPrima = materiaPrimaRepository.findById(ingredienteDTO.getId())
                            .orElseThrow(() -> new Exception("Matéria-Prima não encontrada com o ID: " + ingredienteDTO.getId()));

                    Receita receita = new Receita();
                    ReceitaKey receitaKey = new ReceitaKey();
                    receitaKey.setProduto_id(produto);
                    receitaKey.setMateriaPrima_id(materiaPrima);
                    receita.setId(receitaKey);
                    receita.setQuantidadeNecessaria(ingredienteDTO.getQuantidade());
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
        dto.setQuantidadeEstoque(produto.getQuantidadeEstoque());
        dto.setImagens(produto.getUrlImagemList()
                .stream().map(URLImagem -> new ExibicaoImagemDTO(URLImagem.getUrl(), URLImagem.getPrincipal()))
                .collect(Collectors.toList()));

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    public ResponseEntity<ExibicaoProdutoDTO> preVisualizacaoProduto(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Produto não encontrado!"));

        ExibicaoProdutoDTO dto = new ExibicaoProdutoDTO();
        dto.setId(produto.getId());
        dto.setQuantidadeEstoque(produto.getQuantidadeEstoque());
        dto.setStatus(produto.getStatus());
        dto.setNome(produto.getNome());
        dto.setPreco(produto.getPreco());
        dto.setDescricao(produto.getDescricao());
        dto.setAvaliacao(produto.getAvaliacao());
        dto.setImagens(produto.getUrlImagemList()
                .stream().map(URLImagem::getUrl)
                .collect(Collectors.toList()));
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    public ResponseEntity<List<ExibicaoProdutoDTO>> preVisualizacaoTodosProdutos() {
        List<Produto> produtos = produtoRepository.findAll();

        List<ExibicaoProdutoDTO> produtosDTO = produtos.stream().map(produto -> {
            ExibicaoProdutoDTO dto = new ExibicaoProdutoDTO();
            dto.setId(produto.getId());
            dto.setNome(produto.getNome());
            dto.setCategoria(produto.getCategoria());
            dto.setPreco(produto.getPreco());
            dto.setStatus(produto.getStatus());
            dto.setDescricao(produto.getDescricao());
            dto.setAvaliacao(produto.getAvaliacao());
            dto.setQuantidadeEstoque(produto.getQuantidadeEstoque());
            dto.setImagens(produto.getUrlImagemList()
                    .stream().map(URLImagem::getUrl)
                    .collect(Collectors.toList()));
            return dto;
        }).collect(Collectors.toList());

        return new ResponseEntity<>(produtosDTO, HttpStatus.OK);
    }


    public ResponseEntity<?> ativaDesativaProduto(Long id) throws NoSuchElementException {

        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Produto não encontrado!"));

        if (produto != null) {
            produto.setStatus(!produto.getStatus());
            produtoRepository.save(produto);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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


    @Transactional
    public ResponseEntity<?> confeccionaProduto(Long id, int quantidade) throws NoSuchElementException {

        List<IngredienteDTO> receita = receitaRepository.findIngredientesByProdutoId(id);
        List<MateriaPrimaParaConsumoDTO> estoque = materiaPrimaRepository.materiasPrimasParaConfeccao(
                receita.stream().map(IngredienteDTO::getId).collect(Collectors.toList()));
        List<MateriaPrimaParaConsumoDTO> ingredientesFaltantes = new ArrayList<>();

        for (IngredienteDTO ingrediente : receita) {

            BigDecimal quantidadeNecessaria = ingrediente.getQuantidade().multiply(BigDecimal.valueOf(quantidade));

            MateriaPrimaParaConsumoDTO materiaPrima = estoque.stream()
                    .filter(mp -> mp.getId().equals(ingrediente.getId()))
                    .findFirst()
                    .orElseThrow(() -> new NoSuchElementException("Ingrediente não encontrado no estoque: " + ingrediente.getId()));

            if (materiaPrima.getQuantidadeEstoque().compareTo(quantidadeNecessaria) < 0) {
                ingredientesFaltantes.add(materiaPrima);
            }
        }

        if(ingredientesFaltantes.size() > 0)
            return new ResponseEntity<>(ingredientesFaltantes, HttpStatus.UNAUTHORIZED);
        else {
            for (IngredienteDTO ingrediente : receita) {
                BigDecimal quantidadeNecessaria = ingrediente.getQuantidade().multiply(BigDecimal.valueOf(quantidade));

                ResponseEntity<?> response = materiaPrimaService.consumirMateriaPrima(ingrediente.getId(), quantidadeNecessaria);

                if (response.getStatusCode() != HttpStatus.OK) {
                    return ResponseEntity.badRequest().body("Erro ao consumir matéria-prima para o ingrediente de id: " + ingrediente.getId());
                }
            }
            produtoRepository.adicionarProduto(id, quantidade);
        }

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @Transactional
    public ResponseEntity<?> alterarProduto(Long id, AlterarProdutoReqDTO dto) throws Exception {

        Produto produtoSalvo = produtoRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Produto não encontrado!"));

        if(dto.getNome() != null && !dto.getNome().equals(produtoSalvo.getNome()))
            produtoSalvo.setNome(dto.getNome());

        if (dto.getDescricao() != null && !dto.getDescricao().equals(produtoSalvo.getDescricao()))
            produtoSalvo.setDescricao(dto.getDescricao());

        if(dto.getPreco() != null && dto.getPreco().compareTo(produtoSalvo.getPreco()) != 0)
            produtoSalvo.setPreco(dto.getPreco());

        if(dto.getCategoria() != null && !dto.getCategoria().equals(produtoSalvo.getCategoria()))
            produtoSalvo.setCategoria(dto.getCategoria());

        if(dto.getTamanho() != null && !dto.getTamanho().equals(produtoSalvo.getTamanho()))
            produtoSalvo.setTamanho(dto.getTamanho());

        if(dto.getAvaliacao() != null && dto.getAvaliacao().compareTo(produtoSalvo.getAvaliacao()) != 0)
            produtoSalvo.setAvaliacao(dto.getAvaliacao());

        if (!dto.getUrlImagensExcluidas().isEmpty()) {
            for (String url : dto.getUrlImagensExcluidas()) {
                URLImagem imagemSalva = urlRepository.findByUrl(url, id);
                if (imagemSalva != null) {
                    urlRepository.deleteById(imagemSalva.getId());
                    produtoSalvo.getUrlImagemList().remove(imagemSalva);
                    logger.info("Imagem excluída.");
                } else {
                    logger.warning("Imagem não encontrada para a URL: " + url);
                }
            }
        }

        if (dto.getImagemPrincipal() != null) {
            uploadImage(dto.getImagemPrincipal(), produtoSalvo, true);
        }else {
            logger.warning("Imagem principal não foi alterada.");
        }

        if (dto.getImagensNovas() != null && !dto.getImagensNovas().isEmpty()) {
            for (MultipartFile imagem : dto.getImagensNovas()) {
                if (imagem != null && !imagem.isEmpty()) {
                    uploadImage(imagem, produtoSalvo,false);
                } else {
                    logger.warning("Imagem adicional fornecida está vazia ou nula");
                }
            }
        } else {
            logger.info("Nenhuma imagem adicional fornecida");
        }

        produtoRepository.save(produtoSalvo);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @Transactional
    public boolean consumirProduto(Produto produto, Integer quantidade) throws Exception {

        if(produto.getQuantidadeEstoque() >= quantidade) {
            produtoRepository.consumirProduto(produto.getId(), quantidade);
            logger.info("Estoque atualizado com sucesso!");
            return true;
        } else {
            logger.warning("Estoque insuficiente: Produto - " + produto.getNome() + ", Qtdd - " + produto.getQuantidadeEstoque());
            return false;
        }

    }
}
