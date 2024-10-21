package com.pi.mafu_bakery_api.service;

import com.pi.mafu_bakery_api.dto.EnderecoDTO;
import com.pi.mafu_bakery_api.interfaces.IEndereco;
import com.pi.mafu_bakery_api.key.EnderecoClienteKey;
import com.pi.mafu_bakery_api.model.Cliente;
import com.pi.mafu_bakery_api.model.Endereco;
import com.pi.mafu_bakery_api.model.EnderecoCliente;
import com.pi.mafu_bakery_api.repository.ClienteRepository;
import com.pi.mafu_bakery_api.repository.EnderecoClienteRepository;
import com.pi.mafu_bakery_api.repository.EnderecoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class EnderecoService implements IEndereco {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoClienteRepository enderecoClienteRepository;

    @Transactional
    public ResponseEntity<Endereco> cadastrarEnderecoClienteLogado(EnderecoDTO dto, Long id) {

        Cliente clienteAutenticado = clienteRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Cliente não encontrado com o id: " + id));
        if(clienteAutenticado != null) {
            if(!checaSeOsParametrosDeEntradaNaoSaoNulos(dto)) {
                if (dto.getPrincipal() != null && dto.getPrincipal()) {
                    Endereco enderecoPrincipalExistente = enderecoClienteRepository.findEnderecoPrincipalPorCliente(clienteAutenticado.getId());
                    if (enderecoPrincipalExistente != null) {
                        enderecoPrincipalExistente.setPrincipal(false);
                        enderecoRepository.save(enderecoPrincipalExistente);
                    }
                }

                Endereco novoEndereco = new Endereco();
                novoEndereco.setCep(dto.getCep());
                novoEndereco.setRua(dto.getRua());
                novoEndereco.setNumero(dto.getNumero());
                novoEndereco.setBairro(dto.getBairro());
                novoEndereco.setCidade(dto.getCidade());
                novoEndereco.setUf(dto.getUf());
                novoEndereco.setPrincipal(dto.getPrincipal());
                novoEndereco.setTipo(dto.getTipo());
                if(dto.getComplemento() != null) {
                    novoEndereco.setComplemento(dto.getComplemento());
                }

                enderecoRepository.save(novoEndereco);
                EnderecoClienteKey enderecoClienteKey = new EnderecoClienteKey(novoEndereco, clienteAutenticado);
                EnderecoCliente enderecoCliente = new EnderecoCliente();
                enderecoCliente.setId(enderecoClienteKey);
                enderecoClienteRepository.save(enderecoCliente);
                return new ResponseEntity<>(novoEndereco, HttpStatus.CREATED);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    public ResponseEntity<List<Endereco>> retornaEnderecosCliente(Long id) {
        try {
            Cliente cliente = new Cliente();
            cliente.setId(id);
            List<EnderecoCliente> enderecosRetornados = enderecoRepository.retornaListaEnderecosCliente(cliente);

            if (!enderecosRetornados.isEmpty()) {
                List<Endereco> enderecoModels = new ArrayList<>();

                for (EnderecoCliente enderecoCliente : enderecosRetornados) {
                    enderecoModels.add(enderecoCliente.getId().getEnderecoId());
                }
                return new ResponseEntity<>(enderecoModels, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            System.out.println("Erro ao retornar lista de endereços: " + ex.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Transactional
    public ResponseEntity<Endereco> defineEnderecoPrincipal(Long enderecoId, Long clienteId) {
        Endereco endereco = enderecoRepository.findById(enderecoId).orElseThrow(
                () -> new NoSuchElementException("Endereço não encontrado com o id: " + enderecoId));

        Cliente cliente = clienteRepository.findById(clienteId).orElseThrow(
                () -> new NoSuchElementException("Cliente não encontrado com o id: " + clienteId));

        Endereco enderecoPrincipalExistente = enderecoClienteRepository.findEnderecoPrincipalPorCliente(cliente.getId());
        if (enderecoPrincipalExistente != null) {
            enderecoPrincipalExistente.setPrincipal(false);
            enderecoRepository.save(enderecoPrincipalExistente);
        }

        endereco.setPrincipal(true);
        enderecoRepository.save(endereco);
        return new ResponseEntity<>(endereco, HttpStatus.OK);
    }

    private boolean checaSeOsParametrosDeEntradaNaoSaoNulos(EnderecoDTO dto) {
        return dto == null || dto.getRua() == null || dto.getPrincipal() == null ||
                dto.getUf() == null || dto.getCep() == null || dto.getBairro() == null ||
                dto.getCidade() == null || dto.getNumero() == null || dto.getTipo() == null;
    }
}
