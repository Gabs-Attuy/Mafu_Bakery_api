package com.pi.mafu_bakery_api.repository;

import com.pi.mafu_bakery_api.dto.ProdutoResumoDTO;
import com.pi.mafu_bakery_api.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Query("SELECT new com.pi.mafu_bakery_api.dto.ProdutoResumoDTO(p.id, p.nome, p.quantidadeEstoque, p.preco, p.status) " +
            "FROM Produto p ORDER BY p.id DESC")
    List<ProdutoResumoDTO> listarProdutosDesc();

    @Modifying
    @Query("UPDATE Produto p SET p.quantidadeEstoque = p.quantidadeEstoque + :novaQuantidade WHERE p.id = :id AND p.status = true")
    void adicionarProduto(@Param("id") Long id, @Param("novaQuantidade") int novaQuantidade);

}
