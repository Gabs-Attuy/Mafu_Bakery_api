package com.pi.mafu_bakery_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pi.mafu_bakery_api.dto.*;
import com.pi.mafu_bakery_api.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/cliente")
public class ClienteController {

    @Autowired
    ClienteService clienteService;

    @PostMapping(path = "/cadastro", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> cadastro (
            @RequestPart("cliente") String clienteJson,
            @RequestPart("enderecos") String enderecosJson) throws Exception {

        ClienteDTO dadosCliente = new ObjectMapper().readValue(clienteJson, ClienteDTO.class);
        List<EnderecoDTO> enderecosCliente = Arrays.asList(new ObjectMapper().readValue(enderecosJson, EnderecoDTO[].class));

        return clienteService.cadastro(dadosCliente, enderecosCliente);
    }

    @PutMapping("/alterarCliente")
    public ResponseEntity<AlteracaoClienteDTO> alterarDadosCliente(@RequestParam("id") Long id,
                                                                   @RequestBody AlteracaoClienteDTO dto) throws Exception {
        return clienteService.alterarDadosCliente(id, dto);
    }

    @GetMapping("/infoCliente")
    public ResponseEntity<ClienteInfoDTO> retornaDadosCliente(@RequestParam("email") String email) throws Exception {
        return clienteService.retornaDadosCliente(email);
    }

    @PatchMapping("/alteraSenha")
    public ResponseEntity<?> alteraSenha(@RequestParam ("id") Long id,
                                         @RequestBody @Valid AlteraSenhaClienteDTO dto) throws NoSuchElementException {
        return clienteService.alterarSenha(id, dto);
    }
}