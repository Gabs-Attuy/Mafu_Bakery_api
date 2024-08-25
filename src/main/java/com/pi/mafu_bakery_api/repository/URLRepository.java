package com.pi.mafu_bakery_api.repository;

import com.pi.mafu_bakery_api.model.URLImagem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface URLRepository extends JpaRepository<URLImagem, Long> {}
