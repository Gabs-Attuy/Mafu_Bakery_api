package com.pi.mafu_bakery_api.service;

import com.pi.mafu_bakery_api.dto.AlteracaoDTO;
import com.pi.mafu_bakery_api.dto.AlteracaoUsuarioDTO;
import com.pi.mafu_bakery_api.dto.CadastroUsuarioDTO;
import com.pi.mafu_bakery_api.dto.ListaUsuariosDTO;
import com.pi.mafu_bakery_api.enums.RoleEnum;
import com.pi.mafu_bakery_api.model.*;
import com.pi.mafu_bakery_api.repository.*;
import com.pi.mafu_bakery_api.security.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.management.relation.Role;
import java.util.List;
import java.util.Objects;

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
    private JwtTokenProvider jwtTokenProvider;

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
        credencial.setIsEnabled(true);
        Permissao permissao = new Permissao();
        permissao.setId(3L);
        permissao.setPermissao(RoleEnum.CLIENTE);
        credencial.setPermissao(permissao);
        credencialRepository.save(credencial);

        Carrinho carrinho = new Carrinho();
        carrinho.setUsuario_id(usuario);
        carrinho.setQuantidadeItens(0);
        carrinhoRepository.save(carrinho);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public ResponseEntity<Usuario> cadastrarUsuario(CadastroUsuarioDTO dto) throws Exception {
        Usuario usuario = new Usuario();
        Credencial credencial = new Credencial();

        usuario.setNome(dto.getNome());
        usuario.setCpf(dto.getCpf());
        usuarioRepository.save(usuario);

        Usuario id = usuarioRepository.findById(usuario.getId()).
                orElseThrow(() -> new Exception("Usuário não encontrado"));

        credencial.setUsuario(id);
        credencial.setEmail(dto.getEmail());
        credencial.setSenha(encryptPassword(dto.getSenha()));
        credencial.setIsEnabled(true);
        Permissao permissao = new Permissao();
        RoleEnum roleEnum = RoleEnum.valueOf(String.valueOf(dto.getPermissao()));
        permissao.setPermissao(roleEnum);

        if (roleEnum == RoleEnum.ADMINISTRADOR)
            permissao.setId(1L);
        else
            permissao.setId(2L);
        credencial.setPermissao(permissao);
        credencialRepository.save(credencial);
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


    public ResponseEntity<Usuario> alterarUsuario(String  email, AlteracaoDTO dto, HttpServletRequest request) throws Exception {

        String emailAutenticado = jwtTokenProvider.validateToken(jwtTokenProvider.resolveToken(request));

        if (emailAutenticado == null || emailAutenticado.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Credencial credencial = credencialRepository.findUsuarioByEmail(email);
        Usuario usuario = usuarioRepository.findById(credencial.getId()).orElseThrow( () -> new Exception("usuario nao encontrado"));
        Permissao permissao = new Permissao();
        RoleEnum roleEnum = RoleEnum.valueOf(String.valueOf(dto.getPermissao()));


        if(usuario != null){
            usuario.setNome(dto.getNome());
            if(dto.getCpf() != null && !dto.getCpf().equals(usuario.getCpf())){
                Usuario usuarioExistente = usuarioRepository.buscaPorCPF(dto.getCpf());
                if(usuarioExistente != null && !usuarioExistente.getId().equals(usuario.getId())){
                    return new ResponseEntity<>(HttpStatus.CONFLICT);
                }
            }
            usuario.setCpf(dto.getCpf());

            if(emailAutenticado.equals(email))
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            else
                permissao.setPermissao(roleEnum);

            if (roleEnum == RoleEnum.ADMINISTRADOR)
                permissao.setId(1L);
            else
                permissao.setId(2L);

            credencial.setPermissao(permissao);

            usuarioRepository.save(usuario);
            credencialRepository.save(credencial);

            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    public ResponseEntity<?> ativaDesativaUsuario(Long id) throws Exception {

        Credencial credencial = credencialRepository.findByIdUsuario(id);

        if(credencial != null) {
            if(credencial.getIsEnabled()){
                credencial.setIsEnabled(false);
            } else {
                credencial.setIsEnabled(true);
            }
            credencialRepository.save(credencial);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
}
