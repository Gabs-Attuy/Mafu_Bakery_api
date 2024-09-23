package com.pi.mafu_bakery_api.service;

import com.pi.mafu_bakery_api.dto.*;
import com.pi.mafu_bakery_api.interfaces.IMateriaPrimaService;
import com.pi.mafu_bakery_api.model.MateriaPrima;
import com.pi.mafu_bakery_api.model.Produto;
import com.pi.mafu_bakery_api.model.URLImagem;
import com.pi.mafu_bakery_api.repository.MateriaPrimaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class MateriaPrimaService implements IMateriaPrimaService {

    @Autowired
    private MateriaPrimaRepository materiaPrimaRepository;

    public ResponseEntity<MateriaPrima> cadastrarMateriaPrima(CadastroMateriaPrimaDTO dto) throws Exception {

        try{
            if(checaSeOsParametrosDeEntradaNaoSaoNulos(dto))
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

            MateriaPrima materiaPrima = new MateriaPrima();
            materiaPrima.setNome(dto.getNome());
            materiaPrima.setDescricao(dto.getDescricao());
            materiaPrima.setPreco(dto.getPreco());
            materiaPrima.setQuantidadeEstoque(dto.getQuantidadeEstoque());
            materiaPrima.setUnidadeMedida(dto.getUnidadeMedida());
            materiaPrima.setStatus(true);
            materiaPrimaRepository.save(materiaPrima);

            return new ResponseEntity<>(HttpStatus.CREATED);

        } catch (Exception e) {
            throw new Exception("Erro ao cadastrar matéria prima: " + e);
        }

    }

    public ResponseEntity<List<ListaMateriaPrimaDTO>> listarMateriasPrimas() {

        return new ResponseEntity<>(materiaPrimaRepository.listarMateriaPrima(), HttpStatus.OK);

    }

    @Transactional
    public ResponseEntity<?> alterarMateriaPrima(Long id, AlteracaoMateriaPrimaDTO dto) throws NoSuchElementException {

        MateriaPrima materiaPrima = materiaPrimaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Matéria prima não encontrada!"));

        if(materiaPrima != null) {

            if(dto.getNome() != null && !dto.getNome().equals(materiaPrima.getNome()))
                materiaPrima.setNome(dto.getNome());

            if(dto.getDescricao() != null && !dto.getDescricao().equals(materiaPrima.getDescricao()))
                materiaPrima.setDescricao(dto.getDescricao());

            if(dto.getPreco() != null && dto.getPreco().compareTo(materiaPrima.getPreco()) != 0)
                materiaPrima.setPreco(dto.getPreco());

            if (dto.getUnidadeMedida() != null && !dto.getUnidadeMedida().equals(materiaPrima.getUnidadeMedida()))
                materiaPrima.setUnidadeMedida(dto.getUnidadeMedida());

            materiaPrimaRepository.save(materiaPrima);

            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @Transactional
    public ResponseEntity<?> adicionarNovaQuantidadeMateriaPrima(Long id, BigDecimal novaQuantidade) throws NoSuchElementException {

        MateriaPrima materiaPrima = materiaPrimaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Matéria prima não encontrada!"));

        if (materiaPrima != null) {
            materiaPrimaRepository.adicionarMateriaPrima(id, novaQuantidade);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @Transactional
    public ResponseEntity<?> consumirMateriaPrima(Long id, BigDecimal quantidadeNecessaria) throws NoSuchElementException {

        MateriaPrima materiaPrima = materiaPrimaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Matéria prima não encontrada!"));

        if (materiaPrima != null) {
            if(materiaPrima.getQuantidadeEstoque().compareTo(quantidadeNecessaria) >= 0) {
                materiaPrimaRepository.consumirMateriaPrima(id, quantidadeNecessaria);
                return new ResponseEntity<>(HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    public ResponseEntity<?> ativaDesativaMateriaPrima(Long id) throws NoSuchElementException {

        MateriaPrima materiaPrima = materiaPrimaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Matéria prima não encontrada!"));

        if(materiaPrima != null) {
            materiaPrima.setStatus(!materiaPrima.getStatus());
            materiaPrimaRepository.save(materiaPrima);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    private boolean checaSeOsParametrosDeEntradaNaoSaoNulos(CadastroMateriaPrimaDTO dto) {

        return dto == null || dto.getNome() == null || dto.getDescricao() == null ||
                dto.getPreco() == null || dto.getQuantidadeEstoque() == null;

    }

   public ResponseEntity<MateriaPrima> retornoIngrediente(Long id) {
        MateriaPrima materiaPrima = materiaPrimaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Matéria prima não encontrada!"));
        return new ResponseEntity<>(materiaPrima, HttpStatus.OK);
   }
}
