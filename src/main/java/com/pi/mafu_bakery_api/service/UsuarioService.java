package com.pi.mafu_bakery_api.service;

import com.pi.mafu_bakery_api.dto.CadastroUsuarioDTO;
import com.pi.mafu_bakery_api.enums.PermissaoEnum;
import com.pi.mafu_bakery_api.model.Credencial;
import com.pi.mafu_bakery_api.model.Permissao;
import com.pi.mafu_bakery_api.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public ResponseEntity<Usuario> cadastrarUsuario(CadastroUsuarioDTO dto) {

        Usuario usuario = new Usuario();
        Credencial credencial = new Credencial();

        usuario.setNome(dto.getNome());
        usuario.setCpf(dto.getCpf());
        usuario.setCelular("011éoDDD");
        usuarioRepository.save(usuario);

        credencial.setEmail(dto.getEmail());
        credencial.setSenha(bCryptPasswordEncoder.encode(dto.getSenha()));
        credencial.setPermissao(new Permissao(usuarioRepository.findById(usuario.getId()),
                PermissaoEnum.USUARIO_COMUM));
        credencial.isEnabled();
        credencialRepository.save(credencial);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
