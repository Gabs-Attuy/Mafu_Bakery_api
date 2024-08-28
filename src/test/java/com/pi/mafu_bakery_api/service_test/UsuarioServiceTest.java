package com.pi.mafu_bakery_api.service_test;

import com.pi.mafu_bakery_api.dto.CadastroUsuarioDTO;
import com.pi.mafu_bakery_api.enums.RoleEnum;
import com.pi.mafu_bakery_api.model.Credencial;
import com.pi.mafu_bakery_api.model.Permissao;
import com.pi.mafu_bakery_api.model.Usuario;
import com.pi.mafu_bakery_api.repository.UsuarioRepository;
import com.pi.mafu_bakery_api.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static jdk.internal.org.objectweb.asm.util.CheckClassAdapter.verify;
import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class UsuarioServiceTest {

    @Autowired
    private UsuarioService usuarioService;

    @MockBean
    private UsuarioRepository usuarioRepository;

    @Test
    void deveCadastrarUmUsuarioNoBancoERetornarUmJson() {

        CadastroUsuarioDTO usuarioDTO = new CadastroUsuarioDTO();
        usuarioDTO.setNome("Joao");
        usuarioDTO.setCpf("48436782879");
        usuarioDTO.setEmail("joao123@gmail.com");
        usuarioDTO.setSenha("joao123");

        Usuario usuario = new Usuario();
        Credencial credencial = new Credencial();
        usuario.setId(1L);
        usuario.setNome("Joao");
        usuario.setCpf("48436782879");
        credencial.setEmail("joao123@gmail.com");
        credencial.setSenha("joao123");
        credencial.setPermissao(new Permissao(1L, RoleEnum.CLIENTE));

        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        // Chamar o método a ser testado
        Usuario usuarioSalvo = usuarioService.(usuarioDTO);

        // Verificar o resultado
        assertEquals("João Silva", usuarioSalvo.getNome());
        assertEquals("joao.silva@example.com", usuarioSalvo.getEmail());

        // Verificar se o método save do repository foi chamado
        verify(usuarioRepository, times(1)).save(any((Usuario.class)));

    }
}
