package com.pi.mafu_bakery_api.repository;

import com.pi.mafu_bakery_api.enums.TipoEndereco;
import com.pi.mafu_bakery_api.key.EnderecoClienteKey;
import com.pi.mafu_bakery_api.model.Endereco;
import com.pi.mafu_bakery_api.model.EnderecoCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EnderecoClienteRepository extends JpaRepository<EnderecoCliente, EnderecoClienteKey> {

    @Query("SELECT e FROM Endereco e JOIN EnderecoCliente ec ON e.id = ec.id.enderecoId.id WHERE ec.id.clienteId.id = :clienteId AND e.principal = true")
    Endereco findEnderecoPrincipalPorCliente(@Param("clienteId") Long clienteId);

    @Query("SELECT ec.endereco FROM EnderecoCliente ec WHERE ec.cliente.id = :clienteId AND ec.endereco.tipo = :tipo")
    List<Endereco> findEnderecosByClienteIdAndTipo(@Param("clienteId") Long clienteId, @Param("tipo") TipoEndereco tipo);

}
