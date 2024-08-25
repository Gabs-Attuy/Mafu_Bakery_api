package com.pi.mafu_bakery_api.service;

import com.pi.mafu_bakery_api.dto.CadastroUsuarioDTO;
import com.pi.mafu_bakery_api.enums.PermissaoEnum;
import com.pi.mafu_bakery_api.key.PermissaoUsuarioKey;
import com.pi.mafu_bakery_api.model.*;
import com.pi.mafu_bakery_api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static com.pi.mafu_bakery_api.model.Credencial.encryptPassword;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CredencialRepository credencialRepository;

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @Autowired
    private PermissaoRepository permissaoRepository;

    @Autowired
    PermissaoUsuarioRepository permissaoUsuarioRepository;

    public ResponseEntity<Usuario> cadastrarUsuario(CadastroUsuarioDTO dto) throws Exception {

        Usuario usuario = new Usuario();
        Credencial credencial = new Credencial();

        usuario.setNome(dto.getNome());
        usuario.setCpf(dto.getCpf());
        usuario.setCelular(dto.getCelular());
        usuarioRepository.save(usuario);

        Usuario id = usuarioRepository.findById(usuario.getId()).orElseThrow(() -> new Exception("NÃO ENCONTRADO"));

        credencial.setUsuario(id);
        credencial.setEmail(dto.getEmail());
        credencial.setSenha(encryptPassword(dto.getSenha()));
        credencial.setIsEnabled(true);
        credencialRepository.save(credencial);

        Carrinho carrinho = new Carrinho();
        carrinho.setUsuario_id(usuario); // Use o objeto de usuário
        carrinho.setQuantidadeItens(0);
        carrinhoRepository.save(carrinho);

        Permissao permissao = new Permissao();
        Long localizarPermissao = permissaoRepository.findPermissaoById(1L).getId();
        permissao.setId(localizarPermissao);
        permissao.setDescricao(PermissaoEnum.USUARIO_COMUM);

        PermissaoUsuarioKey permissaoUsuariokey = new PermissaoUsuarioKey(permissao, usuario);
        PermissaoUsuario permissaoUsuario = new PermissaoUsuario();
        permissaoUsuario.setId(permissaoUsuariokey);
        permissaoUsuarioRepository.save(permissaoUsuario);

        return new ResponseEntity<>(usuario, HttpStatus.CREATED);
    }
}
