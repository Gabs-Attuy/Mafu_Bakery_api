package com.pi.mafu_bakery_api.repository;

import com.pi.mafu_bakery_api.key.PermissaoUsuarioKey;
import com.pi.mafu_bakery_api.model.PermissaoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissaoUsuarioRepository extends JpaRepository<PermissaoUsuario, PermissaoUsuarioKey> {
}
