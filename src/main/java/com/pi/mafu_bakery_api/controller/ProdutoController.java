package com.pi.mafu_bakery_api.controller;

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

@CrossOrigin("*")
@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {
    @Autowired
    private ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

//    @PostMapping("/upload")
//    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
//        try {
//            String imageUrl = produtoService.uploadImage(file);
//            return ResponseEntity.ok(imageUrl);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao fazer upload da imagem: " + e.getMessage());
//        }
//    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Produto> cadastraProduto(
            @RequestPart("produto") CadastroProdutoDTO produtoDTO,
            @RequestPart("imagens") List<MultipartFile> imagens) throws Exception {

        produtoDTO.setImagens(imagens);
        return produtoService.cadastraProduto(produtoDTO);
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResumoDTO>> listarProdutos() {
        return produtoService.listarProdutos();
    }
}