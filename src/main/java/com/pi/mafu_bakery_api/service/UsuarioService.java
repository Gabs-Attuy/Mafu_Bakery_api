package com.pi.mafu_bakery_api.service;

import com.pi.mafu_bakery_api.dto.AlteracaoUsuarioDTO;
import com.pi.mafu_bakery_api.dto.CadastroUsuarioDTO;
import com.pi.mafu_bakery_api.dto.ListaUsuariosDTO;
import com.pi.mafu_bakery_api.enums.RoleEnum;
import com.pi.mafu_bakery_api.key.PermissaoUsuarioKey;
import com.pi.mafu_bakery_api.model.*;
import com.pi.mafu_bakery_api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

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

    public ResponseEntity<Usuario> cadastrarCliente(CadastroUsuarioDTO dto) throws Exception {

        Usuario usuario = new Usuario();
        Credencial credencial = new Credencial();

        usuario.setNome(dto.getNome());
        usuario.setCpf(dto.getCpf());
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
        permissao.setPermissao(RoleEnum.CLIENTE);

        PermissaoUsuarioKey permissaoUsuariokey = new PermissaoUsuarioKey(permissao, usuario);
        PermissaoUsuario permissaoUsuario = new PermissaoUsuario();
        permissaoUsuario.setId(permissaoUsuariokey);
        permissaoUsuarioRepository.save(permissaoUsuario);

        return new ResponseEntity<>(usuario, HttpStatus.CREATED);

    }

    public ResponseEntity<List<Usuario>> recuperaTodosUsuarios() {

//        return new ResponseEntity<>(usuarioRepository.listarUsuarios(), HttpStatus.OK);
        return new ResponseEntity<>(usuarioRepository.findAll(), HttpStatus.OK);

    }

    public ResponseEntity<Credencial> alterarSenha(Long id, AlteracaoUsuarioDTO dto) throws Exception {

        Credencial credencial = credencialRepository.findByIdUsuario(id);

        if(credencial != null) {
            if(dto.getSenha() != null && dto.getConfirmaSenha() != null) {
                if (new BCryptPasswordEncoder().matches(dto.getSenha(), credencial.getSenha())){
                    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
                }
                if (dto.getSenha().equals(dto.getConfirmaSenha())) {
                    credencial.setSenha(encryptPassword(dto.getSenha()));
                    credencialRepository.save(credencial);
                    return new ResponseEntity<>(credencial, HttpStatus.OK);
                }
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
}
