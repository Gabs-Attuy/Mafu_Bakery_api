package com.pi.mafu_bakery_api.interfaces;

import com.pi.mafu_bakery_api.dto.*;
import com.pi.mafu_bakery_api.model.Credencial;
import com.pi.mafu_bakery_api.model.Pedido;
import com.pi.mafu_bakery_api.model.Produto;
import com.pi.mafu_bakery_api.model.Usuario;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IUsuarioService {

    ResponseEntity<Usuario> cadastrarCliente(CadastroUsuarioDTO dto) throws Exception;

    ResponseEntity<Usuario> cadastrarUsuario(CadastroUsuarioDTO dto) throws Exception;

    ResponseEntity<List<ListaUsuariosDTO>> recuperaTodosUsuarios();

    ResponseEntity<Credencial> alterarSenha(Long id, AlteracaoUsuarioDTO dto) throws Exception;

    ResponseEntity<Usuario> alterarUsuario(String  email, AlteracaoDTO dto, HttpServletRequest request) throws Exception;

    ResponseEntity<?> ativaDesativaUsuario(Long id) throws Exception;

    ResponseEntity<UsuarioLogadoDTO> recuperaUsuarioPorEmail(String email) throws Exception;

    ResponseEntity<List<ListaUsuariosDTO>> recuperaUsuariosPorPesquisa(String nome) throws Exception;

    ResponseEntity<List<Produto>> listarProdutos() throws Exception;

    ResponseEntity<List<Pedido>> listarPedidos() throws Exception;
}
