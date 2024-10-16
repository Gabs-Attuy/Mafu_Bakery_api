package com.pi.mafu_bakery_api.service;

import com.pi.mafu_bakery_api.dto.*;
import com.pi.mafu_bakery_api.enums.RoleEnum;
import com.pi.mafu_bakery_api.interfaces.IUsuarioService;
import com.pi.mafu_bakery_api.model.*;
import com.pi.mafu_bakery_api.repository.*;
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
    private PermissaoRepository permissaoRepository;

    @Transactional
    public ResponseEntity<Usuario> cadastrarUsuario(CadastroUsuarioDTO dto) throws Exception {

        try{
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
            Permissao permissao = permissaoRepository.findPermissaoByNome(roleEnum);
            credencial.setPermissao(permissao);
            credencialRepository.save(credencial);

            return new ResponseEntity<>(HttpStatus.CREATED);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public ResponseEntity<List<ListaUsuariosDTO>> recuperaTodosUsuarios() {

        return new ResponseEntity<>(usuarioRepository.listarUsuarios(), HttpStatus.OK);
    }

    public ResponseEntity<List<ListaUsuariosDTO>> recuperaUsuariosPorPesquisa(String nome) {

        return new ResponseEntity<>(usuarioRepository.listarUsuariosPorNome(nome), HttpStatus.OK);

    }

    public ResponseEntity<BuscaUsuarioDTO> buscaUsuario(Long id) throws Exception {

        return new ResponseEntity<>(usuarioRepository.buscarUsuario(id), HttpStatus.OK);

    }

    public ResponseEntity<List<Produto>> listarProdutos() throws Exception {
        return null;
    }

    public ResponseEntity<List<Pedido>> listarPedidos() throws Exception {
        return null;
    }

    @Transactional
    public ResponseEntity<?> alterarUsuario(String  email, AlteracaoDTO dto) throws Exception {

        Credencial credencial = credencialRepository.findUsuarioByEmail(email);
        Usuario usuario = usuarioRepository.findById(credencial.getUsuario().getId()).orElseThrow( () -> new Exception("usuario nao encontrado"));

        if(usuario != null){
            if(dto.getNome() != null && !dto.getNome().equals(usuario.getNome())){
                usuario.setNome(dto.getNome());
            }
            if(dto.getCpf() != null && !dto.getCpf().equals(usuario.getCpf())){
                Usuario usuarioExistente = usuarioRepository.buscaPorCPF(dto.getCpf());
                if(usuarioExistente != null && !usuarioExistente.getId().equals(usuario.getId())){
                    return new ResponseEntity<>(HttpStatus.CONFLICT);
                }
                usuario.setCpf(dto.getCpf());
            }
            if(dto.getSenha() != null && !dto.getSenha().isEmpty()) {
                if (new BCryptPasswordEncoder().matches(dto.getSenha(), credencial.getSenha())){
                    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
                }
                credencial.setSenha(encryptPassword(dto.getSenha()));
            }
            if(dto.getPermissao() != null && !dto.getPermissao().equals(credencial.getPermissao().getPermissao())) {
                RoleEnum roleEnum = RoleEnum.valueOf(String.valueOf(dto.getPermissao()));
                Permissao permissao = permissaoRepository.findPermissaoByNome(roleEnum);
                credencial.setPermissao(permissao);
            }

            usuarioRepository.save(usuario);
            credencialRepository.save(credencial);

            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    public ResponseEntity<?> ativaDesativaUsuario(Long id) throws Exception {

        Credencial credencial = credencialRepository.findByIdUsuario(id);
        if(credencial != null) {
            credencial.setIsEnabled(!credencial.getIsEnabled());
            credencialRepository.save(credencial);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    public ResponseEntity<UsuarioLogadoDTO> recuperaUsuarioPorEmail(String email) throws Exception {

        return new ResponseEntity<>(usuarioRepository.buscarUsuarioPorEmail(email), HttpStatus.OK);
    }
}
