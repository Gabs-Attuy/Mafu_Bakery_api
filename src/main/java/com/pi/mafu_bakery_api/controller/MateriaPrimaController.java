package com.pi.mafu_bakery_api.controller;

import com.pi.mafu_bakery_api.dto.AlteracaoMateriaPrimaDTO;
import com.pi.mafu_bakery_api.dto.CadastroMateriaPrimaDTO;
import com.pi.mafu_bakery_api.dto.ListaMateriaPrimaDTO;
import com.pi.mafu_bakery_api.model.MateriaPrima;
import com.pi.mafu_bakery_api.service.MateriaPrimaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/mp")
public class MateriaPrimaController {

    @Autowired
    private MateriaPrimaService materiaPrimaService;

    @PostMapping
    public ResponseEntity<MateriaPrima> cadastrarMateriaPrima (@RequestBody @Valid CadastroMateriaPrimaDTO dto) throws Exception {
        return materiaPrimaService.cadastrarMateriaPrima(dto);
    }

    @GetMapping
    public ResponseEntity<List<ListaMateriaPrimaDTO>> listarMateriasPrimas() {
        return materiaPrimaService.listarMateriasPrimas();
    }

    @PatchMapping
    public ResponseEntity<?> alterarMateriaPrima (@RequestParam ("id") Long id,
                                                  @RequestBody @Valid AlteracaoMateriaPrimaDTO dto) throws NoSuchElementException {
        return materiaPrimaService.alterarMateriaPrima(id, dto);
    }

    @PatchMapping("/aumentarMp")
    public ResponseEntity<?> adicionarNovaQuantidadeMateriaPrima (@RequestParam ("id") Long id,
                                                                  @RequestParam ("novaQuantidade") BigDecimal novaQuantidade) throws NoSuchElementException {
        return materiaPrimaService.adicionarNovaQuantidadeMateriaPrima(id, novaQuantidade);
    }

    @PatchMapping("/consumirMp")
    public ResponseEntity<?> consumirMateriaPrima (@RequestParam ("id") Long id,
                                                   @RequestParam ("quantidadeNecessaria") BigDecimal quantidadeNecessaria) throws NoSuchElementException {
        return materiaPrimaService.consumirMateriaPrima(id, quantidadeNecessaria);
    }

    @PatchMapping("/statusMp")
    public ResponseEntity<?> ativaDesativaMateriaPrima (@RequestParam ("id") Long id) throws NoSuchElementException {
        return materiaPrimaService.ativaDesativaMateriaPrima(id);
    }

    @GetMapping("/mpPorId")
    public ResponseEntity<MateriaPrima> retornoIngredienteById(@RequestParam ("id") Long id) throws NoSuchElementException{
        return materiaPrimaService.retornoIngrediente(id);
    }
}
