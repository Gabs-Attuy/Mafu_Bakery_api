package com.pi.mafu_bakery_api.interfaces;

import com.pi.mafu_bakery_api.dto.AlteracaoClienteDTO;
import com.pi.mafu_bakery_api.dto.ClienteDTO;
import com.pi.mafu_bakery_api.dto.ClienteInfoDTO;
import com.pi.mafu_bakery_api.dto.EnderecoDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ICliente {

    ResponseEntity<?> cadastro(ClienteDTO clienteDTO, List<EnderecoDTO> enderecoDTO) throws Exception;

    ResponseEntity<AlteracaoClienteDTO> alterarDadosCliente(Long id, AlteracaoClienteDTO dto) throws Exception;

    ResponseEntity<ClienteInfoDTO> retornaDadosCliente(String email) throws Exception;
}
