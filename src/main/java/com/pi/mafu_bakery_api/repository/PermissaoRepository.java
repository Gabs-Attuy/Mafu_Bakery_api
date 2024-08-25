package com.pi.mafu_bakery_api.repository;

import com.pi.mafu_bakery_api.model.Permissao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PermissaoRepository extends JpaRepository<Permissao, Long> {

    @Query("SELECT p FROM Permissao p WHERE p.id = :id")
    Permissao findPermissaoById(@Param("id") Long id);
}
