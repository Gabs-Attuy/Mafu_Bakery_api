package com.pi.mafu_bakery_api.repository;

import com.pi.mafu_bakery_api.dto.ProdutoResumoDTO;
import com.pi.mafu_bakery_api.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Query("SELECT new com.pi.mafu_bakery_api.dto.ProdutoResumoDTO(p.id, p.nome, p.quantidadeEstoque, p.preco, p.status) " +
            "FROM Produto p ORDER BY p.id DESC")
    List<ProdutoResumoDTO> listarProdutosDesc();
}
