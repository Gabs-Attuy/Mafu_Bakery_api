package com.pi.mafu_bakery_api.service;

import com.pi.mafu_bakery_api.model.Credencial;
import com.pi.mafu_bakery_api.repository.CredencialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CredencialService implements UserDetailsService {

    @Autowired
    private CredencialRepository credencialRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Credencial credencial = credencialRepository.findUsuarioByEmail(email);
        if(credencial != null) {
            return credencial;
        }
        throw new UsernameNotFoundException("Usuário não encontrado com o email " + email);
    }
}
