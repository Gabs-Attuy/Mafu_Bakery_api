package com.pi.mafu_bakery_api.service;

import com.pi.mafu_bakery_api.dto.AlteracaoMateriaPrimaDTO;
import com.pi.mafu_bakery_api.dto.CadastroMateriaPrimaDTO;
import com.pi.mafu_bakery_api.dto.ListaMateriaPrimaDTO;
import com.pi.mafu_bakery_api.interfaces.IMateriaPrimaService;
import com.pi.mafu_bakery_api.model.MateriaPrima;
import com.pi.mafu_bakery_api.repository.MateriaPrimaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

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

    public ResponseEntity<?> alterarMateriaPrima(Long id, AlteracaoMateriaPrimaDTO dto) throws NoSuchElementException {

        MateriaPrima materiaPrima = materiaPrimaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Matéria prima não encontrada!"));

        if(materiaPrima != null) {
            if(dto.getNome() != null)
                materiaPrima.setNome(dto.getNome());

            if(dto.getDescricao() != null)
                materiaPrima.setDescricao(dto.getDescricao());

            if(dto.getPreco() != null)
                materiaPrima.setPreco(dto.getPreco());

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
}
