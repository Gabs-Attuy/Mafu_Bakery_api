package com.pi.mafu_bakery_api.interfaces;

import com.pi.mafu_bakery_api.dto.CadastroProdutoDTO;
import com.pi.mafu_bakery_api.model.Produto;
import org.springframework.http.ResponseEntity;

public interface IProdutoService {

    ResponseEntity<Produto> cadastraProduto(CadastroProdutoDTO dto) throws Exception;

}
