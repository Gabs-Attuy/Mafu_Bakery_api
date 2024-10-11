package com.pi.mafu_bakery_api.repository;

import com.pi.mafu_bakery_api.key.EnderecoClienteKey;
import com.pi.mafu_bakery_api.model.EnderecoCliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoClienteRepository extends JpaRepository<EnderecoCliente, EnderecoClienteKey> {
}
