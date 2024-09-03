package com.pi.mafu_bakery_api.service;

import com.pi.mafu_bakery_api.dto.*;
import com.pi.mafu_bakery_api.enums.RoleEnum;
import com.pi.mafu_bakery_api.interfaces.IUsuarioService;
import com.pi.mafu_bakery_api.model.*;
import com.pi.mafu_bakery_api.repository.*;
import com.pi.mafu_bakery_api.security.ProvedorTokenJWT;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

import static com.pi.mafu_bakery_api.model.Credencial.encryptPassword;

@Service
public class UsuarioService implements IUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CredencialRepository credencialRepository;

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @Autowired
    private ProvedorTokenJWT provedorTokenJWT;

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
        credencial.setIsEnabled(true);
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

    public ResponseEntity<List<ListaUsuariosDTO>> recuperaUsuariosPorPesquisa(String nome) {

        return new ResponseEntity<>(usuarioRepository.listarUsuariosPorNome(nome), HttpStatus.OK);

    }

    public ResponseEntity<List<Produto>> listarProdutos() throws Exception {
        return null;
    }

    public ResponseEntity<List<Pedido>> listarPedidos() throws Exception {
        return null;
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

        String emailAutenticado = provedorTokenJWT.validaToken(provedorTokenJWT.preparaHeaderToken(request));

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

    public ResponseEntity<UsuarioLogadoDTO> recuperaUsuarioPorEmail(String email) throws Exception {

        return new ResponseEntity<>(usuarioRepository.buscarUsuarioPorEmail(email), HttpStatus.OK);
    }
}
