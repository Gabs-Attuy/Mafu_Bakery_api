package com.pi.mafu_bakery_api.controller;

import com.pi.mafu_bakery_api.dto.EnderecoDTO;
import com.pi.mafu_bakery_api.model.Endereco;
import com.pi.mafu_bakery_api.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/endereco")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;


    @PostMapping("/cadastrar")
    public ResponseEntity<Endereco> cadastrarEnderecoClienteLogado(@RequestParam("id") Long id, @RequestBody EnderecoDTO dto) {
        return enderecoService.cadastrarEnderecoClienteLogado(dto, id);
    }
}
