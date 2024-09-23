package com.pi.mafu_bakery_api.repository;

import com.pi.mafu_bakery_api.model.URLImagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface URLRepository extends JpaRepository<URLImagem, Long> {

    @Query("SELECT u FROM URLImagem u WHERE u.url = :url AND u.produtoId.id = :produtoId")
    URLImagem findByUrl(String url, Long produtoId);

    @Query("SELECT u FROM URLImagem u WHERE u.principal = true AND u.produtoId.id = :produtoId")
    URLImagem findImagemPrincipal(Long produtoId);
}
