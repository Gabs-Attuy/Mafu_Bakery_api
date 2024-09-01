package com.pi.mafu_bakery_api.service;

import com.pi.mafu_bakery_api.dto.CredenciaisContaDTO;
import com.pi.mafu_bakery_api.dto.TokenDTO;
import com.pi.mafu_bakery_api.model.Credencial;
import com.pi.mafu_bakery_api.repository.CredencialRepository;
import com.pi.mafu_bakery_api.security.ProvedorTokenJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    private final CredencialRepository credencialRepository;
    private final PasswordEncoder encriptadorSenha;
    private final ProvedorTokenJWT provedorTokenJWT;

    public ResponseEntity<?> autenticacao(CredenciaisContaDTO dto) throws Exception {

        try {
            Credencial usuario = credencialRepository.findUsuarioByEmail(dto.getEmail());
            if(encriptadorSenha.matches(dto.getSenha(), usuario.getSenha())) {
                var tokenResponse = new TokenDTO();
                tokenResponse = provedorTokenJWT.criarTokenAcesso(usuario.getEmail());
                return ResponseEntity.ok(tokenResponse);
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
