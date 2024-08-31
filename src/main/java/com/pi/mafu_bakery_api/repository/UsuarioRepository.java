package com.pi.mafu_bakery_api.repository;

import com.pi.mafu_bakery_api.dto.ListaUsuariosDTO;
import com.pi.mafu_bakery_api.model.Credencial;
import com.pi.mafu_bakery_api.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    @Query("SELECT new com.pi.mafu_bakery_api.dto.ListaUsuariosDTO(u.nome, c.email, c.isEnabled, p.permissao) " +
            "FROM Usuario u " +
            "JOIN u.credencial c " +
            "JOIN c.permissao p")
    List<ListaUsuariosDTO> listarUsuarios();

    @Query("SELECT u FROM Usuario u WHERE u.cpf = ?1")
    Usuario buscaPorCPF(String cpf);
}
