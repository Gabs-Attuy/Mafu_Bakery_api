package com.pi.mafu_bakery_api.repository;

import com.pi.mafu_bakery_api.key.EnderecoUsuarioKey;
import com.pi.mafu_bakery_api.model.EnderecoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoUsuarioRepository extends JpaRepository<EnderecoUsuario, EnderecoUsuarioKey> {
}
