package com.pi.mafu_bakery_api.interfaces;

import com.pi.mafu_bakery_api.dto.BuscaProdutoEReceitaDTO;
import com.pi.mafu_bakery_api.dto.CadastroProdutoDTO;
import com.pi.mafu_bakery_api.dto.ExibicaoProdutoDTO;
import com.pi.mafu_bakery_api.model.Produto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.NoSuchElementException;

public interface IProdutoService {

    ResponseEntity<Produto> cadastraProduto(CadastroProdutoDTO dto) throws Exception;

    void uploadImage(MultipartFile imagem, Produto produtoModel, boolean principal) throws Exception;

    ResponseEntity<Map<String, Object>> listarProdutos(int page, int size);

    ResponseEntity<BuscaProdutoEReceitaDTO> buscarProdutoeReceita(Long id) throws NoSuchElementException;

    ResponseEntity<?> ativaDesativaProduto(Long id) throws NoSuchElementException;

    ResponseEntity<Map<String, Object>> buscarProdutoPorNome(String nome, int page, int size);

    ResponseEntity<ExibicaoProdutoDTO> preVisualizacaoProduto(Long id);
}


