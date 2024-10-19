package com.pi.mafu_bakery_api.repository;

import com.pi.mafu_bakery_api.model.Cliente;
import com.pi.mafu_bakery_api.model.Endereco;
import com.pi.mafu_bakery_api.model.EnderecoCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    @Query("SELECT e " +
            "FROM EnderecoCliente e " +
            "WHERE e.id.clienteId = :clienteId")
    List<EnderecoCliente> retornaListaEnderecosCliente(@Param("clienteId") Cliente id);
}
