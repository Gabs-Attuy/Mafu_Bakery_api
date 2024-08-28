package com.pi.mafu_bakery_api.repository;

import com.pi.mafu_bakery_api.dto.ListaUsuariosDTO;
import com.pi.mafu_bakery_api.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
//    @Query("SELECT new com.pi.mafu_bakery_api.dto.ListaUsuariosDTO(u.nome, c.email, c.isEnabled, p.descricao) " +
//            "FROM Usuario u " +
//            "JOIN Credencial c ON c.usuario.id = u.id " +
//            "JOIN PermissaoUsuario pu ON pu.usuario.id = u.id " +
//            "JOIN Permissao p ON p.id = pu.permissao.id")
//    List<ListaUsuariosDTO> listarUsuarios();
}
