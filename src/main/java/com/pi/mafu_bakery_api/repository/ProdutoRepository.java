package com.pi.mafu_bakery_api.repository;

import com.pi.mafu_bakery_api.dto.ExibicaoProdutoDTO;
import com.pi.mafu_bakery_api.dto.ProdutoResumoDTO;
import com.pi.mafu_bakery_api.model.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Query("SELECT new com.pi.mafu_bakery_api.dto.ProdutoResumoDTO(p.id, p.nome, p.quantidadeEstoque, p.preco, p.status) " +
            "FROM Produto p ORDER BY p.id DESC")
    List<ProdutoResumoDTO> listarProdutosDesc();

    @Query("SELECT p FROM Produto p WHERE p.nome LIKE %?1%")
    Page<Produto> buscaPorNome(String nomeProduto, Pageable pageable);
}
