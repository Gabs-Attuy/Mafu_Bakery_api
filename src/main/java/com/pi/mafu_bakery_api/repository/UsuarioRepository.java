package com.pi.mafu_bakery_api.repository;

import com.pi.mafu_bakery_api.dto.ListaUsuariosDTO;
import com.pi.mafu_bakery_api.dto.UsuarioLogadoDTO;
import com.pi.mafu_bakery_api.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    @Query("SELECT new com.pi.mafu_bakery_api.dto.ListaUsuariosDTO(u.id, u.nome, c.email, c.isEnabled, p.permissao) " +
            "FROM Usuario u " +
            "JOIN u.credencial c " +
            "JOIN c.permissao p")
    List<ListaUsuariosDTO> listarUsuarios();

    @Query("SELECT new com.pi.mafu_bakery_api.dto.ListaUsuariosDTO(u.id, u.nome, c.email, c.isEnabled, p.permissao) " +
            "FROM Usuario u " +
            "JOIN u.credencial c " +
            "JOIN c.permissao p " +
            "WHERE LOWER(u.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<ListaUsuariosDTO> listarUsuariosPorNome(@Param("nome") String nome);

    @Query("SELECT u FROM Usuario u WHERE u.cpf = ?1")
    Usuario buscaPorCPF(String cpf);

    @Query("SELECT new com.pi.mafu_bakery_api.dto.UsuarioLogadoDTO(u.id, u.nome, p.permissao) " +
            "FROM Usuario u " +
            "JOIN u.credencial c " +
            "JOIN c.permissao p " +
            "WHERE c.email = ?1")
    UsuarioLogadoDTO buscarUsuarioPorEmail(String email);

}
