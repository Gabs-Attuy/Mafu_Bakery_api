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

    private boolean checaSeOsParametrosDeEntradaNaoSaoNulos(EnderecoDTO dto) {
        return dto == null || dto.getRua() == null || dto.getPrincipal() == null ||
                dto.getUf() == null || dto.getCep() == null || dto.getBairro() == null ||
                dto.getCidade() == null || dto.getNumero() == null || dto.getTipo() == null;
    }
}
