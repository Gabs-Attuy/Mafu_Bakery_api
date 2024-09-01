package com.pi.mafu_bakery_api.controller;

import com.pi.mafu_bakery_api.dto.AlteracaoDTO;
import com.pi.mafu_bakery_api.dto.AlteracaoUsuarioDTO;
import com.pi.mafu_bakery_api.dto.CadastroUsuarioDTO;
import com.pi.mafu_bakery_api.dto.ListaUsuariosDTO;
import com.pi.mafu_bakery_api.model.Credencial;
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

    @PutMapping("/alterarSenha")
    public ResponseEntity<Credencial> alterarSenhaUsuario(@RequestParam ("id") Long id,
                                                          @RequestBody AlteracaoUsuarioDTO dto) throws Exception {
        return usuarioService.alterarSenha(id, dto);
    }

    @PutMapping("/ativaDesativaUsuario")
    public ResponseEntity<?> ativaDesativaUsuario(@RequestParam ("id") Long id) throws Exception {
        return usuarioService.ativaDesativaUsuario(id);
    }

    @PutMapping("/alterarUsuario")
    public ResponseEntity<Usuario> alterarUsuario(@RequestParam ("email") String email,
                                                  @RequestBody AlteracaoDTO dto, HttpServletRequest request) throws Exception{
        return usuarioService.alterarUsuario(email, dto, request);
    }

}
