package com.pi.mafu_bakery_api.service;

import com.pi.mafu_bakery_api.dto.*;
import com.pi.mafu_bakery_api.enums.RoleEnum;
import com.pi.mafu_bakery_api.interfaces.ICliente;
import com.pi.mafu_bakery_api.key.EnderecoClienteKey;
import com.pi.mafu_bakery_api.model.*;
import com.pi.mafu_bakery_api.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

        Credencial credencial = new Credencial();
        credencial.setEmail(clienteDTO.getEmail());
        credencial.setSenha(encryptPassword(clienteDTO.getSenha()));

        // Associa a permissão
        RoleEnum roleEnum = RoleEnum.CLIENTE;
        Permissao permissao = permissaoRepository.findPermissaoByNome(roleEnum);
        credencial.setPermissao(permissao);
        credencialRepository.save(credencial); // Salva a credencial antes para obter o ID

        // Agora cria o Cliente e associa a credencial
        Cliente cliente = new Cliente();
        cliente.setNomeCompleto(clienteDTO.getNomeCompleto());
        cliente.setCpf(clienteDTO.getCpf());
        cliente.setDataDeNascimento(clienteDTO.getDataDeNascimento());
        cliente.setGenero(clienteDTO.getGenero());
        cliente.setCredencial(credencial); // Associa a credencial ao cliente
        clienteRepository.save(cliente); // Salva o cliente


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

    @Transactional
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

    public ResponseEntity<ClienteInfoDTO> retornaDadosCliente(String email) throws Exception {
        ClienteBuscaDTO cliente = clienteRepository.buscarClientePorEmail(email);
        if(cliente == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        ClienteInfoDTO dto = new ClienteInfoDTO();

        dto.setId(cliente.getId());
        dto.setNomeCompleto(cliente.getNomeCompleto());
        dto.setCpf(cliente.getCpf());
        dto.setEmail(cliente.getEmail());
        dto.setDataDeNascimento(cliente.getDataDeNascimento());
        dto.setGenero(cliente.getGenero());


        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    public ResponseEntity<?> alterarSenha(Long id, AlteraSenhaClienteDTO dto) {
        Credencial cliente = credencialRepository.findCredencialByClienteId(id);

        if(cliente == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        if(checaSeOsParametrosDeEntradaNaoSaoNulos(dto))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if(new BCryptPasswordEncoder().matches(dto.getSenhaAtual(), cliente.getSenha())) {
            cliente.setSenha(encryptPassword(dto.getSenhaNova()));
            credencialRepository.save(cliente);
            return new ResponseEntity<>(HttpStatus.OK);
        } else
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

    private boolean checaSeOsParametrosDeEntradaNaoSaoNulos(AlteraSenhaClienteDTO dto) {
        return dto == null || dto.getSenhaAtual() == null || dto.getSenhaNova() == null;
    }

    private boolean checaSeOsParametrosDeEntradaNaoSaoNulos(EnderecoDTO dto) {
        return dto == null || dto.getRua() == null || dto.getPrincipal() == null ||
                dto.getUf() == null || dto.getCep() == null || dto.getBairro() == null ||
                dto.getCidade() == null || dto.getNumero() == null || dto.getTipo() == null;
    }

}