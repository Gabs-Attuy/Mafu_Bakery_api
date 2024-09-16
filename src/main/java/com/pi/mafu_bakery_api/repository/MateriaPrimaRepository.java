package com.pi.mafu_bakery_api.repository;

import com.pi.mafu_bakery_api.dto.ListaMateriaPrimaDTO;
import com.pi.mafu_bakery_api.dto.ListaUsuariosDTO;
import com.pi.mafu_bakery_api.model.MateriaPrima;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface MateriaPrimaRepository extends JpaRepository<MateriaPrima, Long> {
    @Query("SELECT new com.pi.mafu_bakery_api.dto.ListaMateriaPrimaDTO(mp.id, mp.nome, mp.quantidadeEstoque, mp.preco, mp.status, mp.unidadeMedida) " +
            "FROM MateriaPrima mp " +
            "ORDER BY mp.id DESC")
    List<ListaMateriaPrimaDTO> listarMateriaPrima();

    @Modifying
    @Query("UPDATE MateriaPrima mp SET mp.quantidadeEstoque = mp.quantidadeEstoque + :novaQuantidade WHERE mp.id = :id")
    void adicionarMateriaPrima(@Param("id") Long id, @Param("novaQuantidade") BigDecimal novaQuantidade);

    @Modifying
    @Query("UPDATE MateriaPrima mp SET mp.quantidadeEstoque = mp.quantidadeEstoque - :quantidadeNecessaria WHERE mp.id = :id AND mp.quantidadeEstoque >= :quantidadeNecessaria")
    void consumirMateriaPrima(@Param("id") Long id, @Param("quantidadeNecessaria") BigDecimal quantidadeNecessaria);

}
