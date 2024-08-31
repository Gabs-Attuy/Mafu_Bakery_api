package com.pi.mafu_bakery_api.repository;

import com.pi.mafu_bakery_api.enums.RoleEnum;
import com.pi.mafu_bakery_api.model.Credencial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CredencialRepository extends JpaRepository<Credencial, Long> {
    @Query("SELECT u FROM Credencial u WHERE u.usuario = ?1")
    Credencial findByIdUsuario(Long id);

    @Query("SELECT u FROM Credencial u WHERE u.email = ?1")
    Credencial findUsuarioByEmail(String email);

    @Query("""
            SELECT p.permissao
            FROM Credencial c
            JOIN c.permissao p
            WHERE c.email = :email""")
    RoleEnum findPermissaoByEmail(@Param("email") String email);
}
