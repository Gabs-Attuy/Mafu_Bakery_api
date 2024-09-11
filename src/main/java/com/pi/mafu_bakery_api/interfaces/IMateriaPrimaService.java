package com.pi.mafu_bakery_api.interfaces;

import com.pi.mafu_bakery_api.dto.*;
import com.pi.mafu_bakery_api.model.MateriaPrima;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

public interface IMateriaPrimaService {

    ResponseEntity<MateriaPrima> cadastrarMateriaPrima(CadastroMateriaPrimaDTO dto) throws Exception;

    ResponseEntity<List<ListaMateriaPrimaDTO>> listarMateriasPrimas();

    ResponseEntity<?> alterarMateriaPrima(Long id, AlteracaoMateriaPrimaDTO dto) throws Exception;

    ResponseEntity<?> adicionarNovaQuantidadeMateriaPrima(Long id, BigDecimal novaQuantidade) throws Exception;

    ResponseEntity<?> consumirMateriaPrima(Long id, BigDecimal quantidadeNecessaria) throws Exception;

    ResponseEntity<?> ativaDesativaMateriaPrima(Long id) throws Exception;

}
