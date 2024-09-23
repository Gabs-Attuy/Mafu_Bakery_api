package com.pi.mafu_bakery_api.repository;

import com.pi.mafu_bakery_api.dto.IngredienteDTO;
import com.pi.mafu_bakery_api.key.ReceitaKey;
import com.pi.mafu_bakery_api.model.Receita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReceitaRepository extends JpaRepository<Receita, ReceitaKey> {
    @Query("SELECT new com.pi.mafu_bakery_api.dto.IngredienteDTO(r.id.materiaPrima_id.id, r.quantidadeNecessaria) " +
            "FROM Receita r WHERE r.id.produto_id.id = :produtoId")
    List<IngredienteDTO> findIngredientesByProdutoId(Long produtoId);
}
