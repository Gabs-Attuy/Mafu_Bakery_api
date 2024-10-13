package com.pi.mafu_bakery_api.service;

import com.pi.mafu_bakery_api.dto.AlteracaoClienteDTO;
import com.pi.mafu_bakery_api.dto.ClienteDTO;
import com.pi.mafu_bakery_api.dto.EnderecoDTO;
import com.pi.mafu_bakery_api.enums.RoleEnum;
import com.pi.mafu_bakery_api.interfaces.ICliente;
import com.pi.mafu_bakery_api.key.EnderecoClienteKey;
import com.pi.mafu_bakery_api.model.*;
import com.pi.mafu_bakery_api.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.pi.mafu_bakery_api.model.Credencial.encryptPassword;

@Service
public class ClienteService implements ICliente {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private EnderecoClienteRepository enderecoClienteRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CredencialRepository credencialRepository;

    @Autowired
    private PermissaoRepository permissaoRepository;

    @Transactional
    public ResponseEntity<?> cadastro(ClienteDTO clienteDTO, List<EnderecoDTO> enderecoDTO) throws Exception {
        if(checaSeOsParametrosDeEntradaNaoSaoNulos(clienteDTO))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Cliente cliente = new Cliente();
        cliente.setNomeCompleto(clienteDTO.getNomeCompleto());
        cliente.setCpf(clienteDTO.getCpf());
        cliente.setDataDeNascimento(clienteDTO.getDataDeNascimento());
        cliente.setGenero(clienteDTO.getGenero());
        clienteRepository.save(cliente);

        Credencial credencial = new Credencial();
        credencial.setCliente(cliente);
        credencial.setEmail(clienteDTO.getEmail());
        credencial.setSenha(encryptPassword(clienteDTO.getSenha()));

        RoleEnum roleEnum = RoleEnum.CLIENTE;
        Permissao permissao = permissaoRepository.findPermissaoByNome(roleEnum);
        credencial.setPermissao(permissao);
        credencialRepository.save(credencial);


        for(EnderecoDTO endereco : enderecoDTO) {
            Endereco enderecos = new Endereco();
            EnderecoCliente relacionamento = new EnderecoCliente();
            EnderecoClienteKey chaveComposta = new EnderecoClienteKey();
            enderecos.setRua(endereco.getRua());
            enderecos.setCep(endereco.getCep());
            enderecos.setBairro(endereco.getBairro());
            enderecos.setCidade(endereco.getCidade());
            enderecos.setNumero(endereco.getNumero());
            enderecos.setUf(endereco.getUf());
            enderecos.setPrincipal(endereco.getPrincipal());
            enderecos.setTipo(endereco.getTipo());
            if(endereco.getComplemento() != null)
                enderecos.setComplemento(endereco.getComplemento());
            enderecoRepository.save(enderecos);

            chaveComposta.setClienteId(cliente);
            chaveComposta.setEnderecoId(enderecos);
            relacionamento.setId(chaveComposta);
            enderecoClienteRepository.save(relacionamento);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public ResponseEntity<AlteracaoClienteDTO> alterarDadosCliente(Long id, AlteracaoClienteDTO dto) throws Exception {
        Cliente clienteAlterado = clienteRepository.findById(id).orElseThrow(
                () -> new Exception("Cliente não encontrado com o id: " + id));

        if(clienteAlterado != null) {
            if(!checaSeOsParametrosDeEntradaNaoSaoNulos(dto)) {
                clienteAlterado.setNomeCompleto(dto.getNomeCompleto());
                clienteAlterado.setGenero(dto.getGenero());
                clienteAlterado.setDataDeNascimento(dto.getDataDeNascimento());
                clienteRepository.save(clienteAlterado);

                return new ResponseEntity<>(dto, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    private boolean checaSeOsParametrosDeEntradaNaoSaoNulos(ClienteDTO dto) {
        return dto == null || dto.getNomeCompleto() == null || dto.getCpf() == null ||
                dto.getEmail() == null || dto.getDataDeNascimento() == null ||
                dto.getGenero() == null || dto.getSenha() == null;
    }

    private boolean checaSeOsParametrosDeEntradaNaoSaoNulos(AlteracaoClienteDTO dto) {
        return dto == null || dto.getNomeCompleto() == null || dto.getDataDeNascimento() == null ||
                dto.getGenero() == null;
    }

    private boolean checaSeOsParametrosDeEntradaNaoSaoNulos(EnderecoDTO dto) {
        return dto == null || dto.getRua() == null || dto.getPrincipal() == null ||
                dto.getUf() == null || dto.getCep() == null || dto.getBairro() == null ||
                dto.getCidade() == null || dto.getNumero() == null || dto.getTipo() == null;
    }



}
