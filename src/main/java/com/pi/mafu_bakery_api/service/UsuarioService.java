package com.pi.mafu_bakery_api.service;

import com.pi.mafu_bakery_api.dto.AlteracaoUsuarioDTO;
import com.pi.mafu_bakery_api.dto.CadastroUsuarioDTO;
import com.pi.mafu_bakery_api.dto.ListaUsuariosDTO;
import com.pi.mafu_bakery_api.enums.RoleEnum;
import com.pi.mafu_bakery_api.model.*;
import com.pi.mafu_bakery_api.repository.*;
import jakarta.transaction.Transactional;
import org.apache.coyote.Response;
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

    @Transactional
    public ResponseEntity<Usuario> cadastrarUsuario(CadastroUsuarioDTO dto) throws Exception {

        if(checaSeOsParametrosDeEntradaNaoSaoNulos(dto))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setCpf(dto.getCpf());
        usuarioRepository.save(usuario);

        Credencial credencial = new Credencial();
        credencial.setUsuario(usuario);
        credencial.setEmail(dto.getEmail());
        credencial.setSenha(encryptPassword(dto.getSenha()));

        RoleEnum roleEnum = RoleEnum.valueOf(String.valueOf(dto.getPermissao()));
        Permissao permissao = permissaoRepository.findPermissaoByNome(String.valueOf(roleEnum));

        credencial.setPermissao(permissao);
        credencialRepository.save(credencial);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    private boolean checaSeOsParametrosDeEntradaNaoSaoNulos(CadastroUsuarioDTO dto) {
        return dto == null || dto.getNome() == null || dto.getCpf() == null ||
                dto.getEmail() == null || dto.getSenha() == null || dto.getPermissao() == null;
    }

    public ResponseEntity<Usuario> cadastrarCliente(CadastroUsuarioDTO dto) throws Exception {

        Usuario usuario = new Usuario();
        Credencial credencial = new Credencial();

        usuario.setNome(dto.getNome());
        usuario.setCpf(dto.getCpf());
        usuarioRepository.save(usuario);

        Usuario id = usuarioRepository.findById(usuario.getId()).
                orElseThrow(() -> new Exception("Cliente não encontrado"));

        credencial.setUsuario(id);
        credencial.setEmail(dto.getEmail());
        credencial.setSenha(encryptPassword(dto.getSenha()));
        credencial.setEnabled(true);
        Permissao permissao = new Permissao();
        permissao.setId(3L);
        permissao.setPermissao(RoleEnum.CLIENTE);
        credencial.setPermissao(permissao);
        credencialRepository.save(credencial);

        Carrinho carrinho = new Carrinho();
        carrinho.setUsuario_id(usuario); // Use o objeto de usuário
        carrinho.setQuantidadeItens(0);
        carrinhoRepository.save(carrinho);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public ResponseEntity<List<ListaUsuariosDTO>> recuperaTodosUsuarios() {

        return new ResponseEntity<>(usuarioRepository.listarUsuarios(), HttpStatus.OK);
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
