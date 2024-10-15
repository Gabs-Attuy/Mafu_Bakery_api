package com.pi.mafu_bakery_api.repository;

import com.pi.mafu_bakery_api.dto.ClienteBuscaDTO;
import com.pi.mafu_bakery_api.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    @Query("SELECT new com.pi.mafu_bakery_api.dto.ClienteBuscaDTO(u.id, u.nomeCompleto, u.cpf, u.dataDeNascimento, u.genero, c.email) " +
            "FROM Cliente u " +
            "JOIN u.credencial c " +
            "WHERE c.email = ?1")
    ClienteBuscaDTO buscarClientePorEmail(String email);
}
