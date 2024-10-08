package com.pi.mafu_bakery_api.security;

import com.pi.mafu_bakery_api.model.Credencial;
import com.pi.mafu_bakery_api.repository.CredencialRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.Collections;

@Component
public class SecurityFilter extends GenericFilterBean {

    @Autowired
    private ProvedorTokenJWT tokenProvider;

    @Autowired
    private CredencialRepository credencialRepository;

    public SecurityFilter(ProvedorTokenJWT tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        try {
            var token = tokenProvider.preparaHeaderToken((HttpServletRequest) request);
            var auth = tokenProvider.validaToken(token);

            if(auth != null) {
                Credencial credencial = credencialRepository.findUsuarioByEmail(auth);
                var autorizacao = credencial.getAuthorities();
                var authentication = new UsernamePasswordAuthenticationToken(credencial, null, autorizacao);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
