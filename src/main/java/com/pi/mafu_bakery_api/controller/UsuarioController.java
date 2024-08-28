package com.pi.mafu_bakery_api.controller;

import com.pi.mafu_bakery_api.dto.AlteracaoUsuarioDTO;
import com.pi.mafu_bakery_api.dto.CadastroUsuarioDTO;
import com.pi.mafu_bakery_api.dto.ListaUsuariosDTO;
import com.pi.mafu_bakery_api.model.Credencial;
import com.pi.mafu_bakery_api.model.Usuario;
import com.pi.mafu_bakery_api.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuario")
@CrossOrigin("*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Usuario> cadastrarUsuario(@RequestBody @Valid CadastroUsuarioDTO dto) throws Exception {
        return usuarioService.cadastrarCliente(dto);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> recuperaTodosUsuarios() {
        return usuarioService.recuperaTodosUsuarios();
    }

    @PutMapping("/alterarSenha")
    public ResponseEntity<Credencial> alterarSenhaUsuario(@RequestParam ("id") Long id,
                                                          @RequestBody AlteracaoUsuarioDTO dto) throws Exception {
        return usuarioService.alterarSenha(id, dto);
    }

}