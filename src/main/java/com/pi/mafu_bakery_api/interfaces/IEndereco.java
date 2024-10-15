package com.pi.mafu_bakery_api.interfaces;

import com.pi.mafu_bakery_api.dto.EnderecoDTO;
import com.pi.mafu_bakery_api.model.Endereco;
import org.springframework.http.ResponseEntity;

public interface IEndereco {

    ResponseEntity<Endereco> cadastrarEnderecoClienteLogado(EnderecoDTO dto, Long id);
}
