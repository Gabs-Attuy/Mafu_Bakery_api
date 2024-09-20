package com.pi.mafu_bakery_api.controller;

import com.pi.mafu_bakery_api.dto.BuscaProdutoEReceitaDTO;
import com.pi.mafu_bakery_api.dto.CadastroProdutoDTO;
import com.pi.mafu_bakery_api.dto.ProdutoResumoDTO;
import com.pi.mafu_bakery_api.model.Produto;
import com.pi.mafu_bakery_api.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Produto> cadastraProduto(
            @RequestPart("produto") CadastroProdutoDTO produtoDTO,
            @RequestPart("imagens") List<MultipartFile> imagens) throws Exception {

        produtoDTO.setImagens(imagens);
        return produtoService.cadastraProduto(produtoDTO);
    }

    @GetMapping("/listagem")
    public ResponseEntity<Map<String, Object>> listarProdutos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return produtoService.listarProdutos(page, size);
    }

    @GetMapping("/recuperaProduto")
    public ResponseEntity<BuscaProdutoEReceitaDTO> buscaProduto (@RequestParam ("id") Long id) throws NoSuchElementException {
        return produtoService.buscarProdutoeReceita(id);
    }

    @PatchMapping("/statusProduto")
    public ResponseEntity<?> ativaDesativaProduto(@RequestParam ("id") Long id) throws NoSuchElementException {
        return produtoService.ativaDesativaProduto(id);
    }

    @GetMapping("/buscarNome")
    public ResponseEntity<Map<String, Object>> buscarProdutoPorNome(
            @RequestParam("nome") String nome,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return produtoService.buscarProdutoPorNome(nome, page, size);
    }
}