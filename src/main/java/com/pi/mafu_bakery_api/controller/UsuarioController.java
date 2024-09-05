package com.pi.mafu_bakery_api.controller;

import com.pi.mafu_bakery_api.dto.*;
import com.pi.mafu_bakery_api.model.Credencial;
import com.pi.mafu_bakery_api.model.Pedido;
import com.pi.mafu_bakery_api.model.Produto;
import com.pi.mafu_bakery_api.model.Usuario;
import com.pi.mafu_bakery_api.service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/cliente")
    public ResponseEntity<Usuario> cadastrarCliente(@RequestBody @Valid CadastroUsuarioDTO dto) throws Exception {
        return usuarioService.cadastrarCliente(dto);
    }

    @PostMapping("/usuario")
    public ResponseEntity<Usuario> cadastrarUsuario(@RequestBody @Valid CadastroUsuarioDTO dto) throws Exception {
        return usuarioService.cadastrarUsuario(dto);
    }

    @GetMapping
    public ResponseEntity<List<ListaUsuariosDTO>> recuperaTodosUsuarios() {
        return usuarioService.recuperaTodosUsuarios();
    }

    @GetMapping("/listarProdutos")
    public ResponseEntity<List<Produto>> listarProdutos() throws Exception {
        return usuarioService.listarProdutos();
    }

    @GetMapping("/buscaUsuario")
    public ResponseEntity<BuscaUsuarioDTO> buscaUsuario(Long id) throws Exception {
        return usuarioService.buscaUsuario(id);
    }

    @GetMapping("/listarPedidos")
    public ResponseEntity<List<Pedido>> listarPedidos() throws Exception {
        return usuarioService.listarPedidos();
    }

    @PutMapping("/alterarSenha")
    public ResponseEntity<Credencial> alterarSenhaUsuario(@RequestParam ("id") Long id,
                                                          @RequestBody AlteracaoUsuarioDTO dto) throws Exception {
        return usuarioService.alterarSenha(id, dto);
    }

    @PutMapping("/ativaDesativaUsuario")
    public ResponseEntity<?> ativaDesativaUsuario(@RequestParam ("id") Long id) throws Exception {
        return usuarioService.ativaDesativaUsuario(id);
    }

    @PatchMapping("/alterarUsuario")
    public ResponseEntity<?> alterarUsuario(@RequestParam ("email") String email,
                                                  @RequestBody AlteracaoDTO dto, HttpServletRequest request) throws Exception{
        return usuarioService.alterarUsuario(email, dto, request);
    }

    @GetMapping("/usuarioLogado")
    public ResponseEntity<UsuarioLogadoDTO> usuarioLogado(@RequestParam ("email") String email) throws Exception {
        return usuarioService.recuperaUsuarioPorEmail(email);
    }

    @GetMapping("/usuariosPorPesquisa")
    public ResponseEntity<List<ListaUsuariosDTO>> recuperaTodosUsuarios(String nome) {
        return usuarioService.recuperaUsuariosPorPesquisa(nome);
    }
}
