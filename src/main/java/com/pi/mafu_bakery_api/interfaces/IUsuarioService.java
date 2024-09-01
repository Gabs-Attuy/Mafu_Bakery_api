package com.pi.mafu_bakery_api.interfaces;

import com.pi.mafu_bakery_api.dto.AlteracaoDTO;
import com.pi.mafu_bakery_api.dto.AlteracaoUsuarioDTO;
import com.pi.mafu_bakery_api.dto.CadastroUsuarioDTO;
import com.pi.mafu_bakery_api.dto.ListaUsuariosDTO;
import com.pi.mafu_bakery_api.model.Credencial;
import com.pi.mafu_bakery_api.model.Usuario;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IUsuarioService {

    ResponseEntity<Usuario> cadastrarCliente(CadastroUsuarioDTO dto) throws Exception;

    ResponseEntity<Usuario> cadastrarUsuario(CadastroUsuarioDTO dto) throws Exception;

    public ResponseEntity<List<ListaUsuariosDTO>> recuperaTodosUsuarios();

    ResponseEntity<Credencial> alterarSenha(Long id, AlteracaoUsuarioDTO dto) throws Exception;

    ResponseEntity<Usuario> alterarUsuario(String  email, AlteracaoDTO dto, HttpServletRequest request) throws Exception;

    ResponseEntity<?> ativaDesativaUsuario(Long id) throws Exception;

}
