package com.pi.mafu_bakery_api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {
        http
                .csrf(csfr -> csfr.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/api/auth/signin").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/usuario").hasAuthority("ADMINISTRADOR")
                        .requestMatchers(HttpMethod.POST, "/api/cliente").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/mp").hasAuthority("ADMINISTRADOR")
                        .requestMatchers(HttpMethod.PATCH, "/api/ativaDesativaUsuario").hasAuthority("ADMINISTRADOR")
                        .requestMatchers(HttpMethod.PATCH, "/api/alterarUsuario").hasAuthority("ADMINISTRADOR")
                        .requestMatchers(HttpMethod.PATCH, "/api/mp").hasAuthority("ADMINISTRADOR")
                        .requestMatchers(HttpMethod.PATCH, "/api/mp/statusMp").hasAuthority("ADMINISTRADOR")
                        .requestMatchers(HttpMethod.PATCH, "/api/mp/aumentarMp").hasAuthority("ESTOQUISTA")
                        .requestMatchers(HttpMethod.PATCH, "/api/mp/consumirMp").hasAuthority("ESTOQUISTA")
                        .requestMatchers(HttpMethod.GET, "/api/usuarioLogado").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api").hasAuthority("ADMINISTRADOR")
                        .requestMatchers(HttpMethod.GET, "/api/listarPedidos").hasAuthority("ESTOQUISTA")
                        .requestMatchers(HttpMethod.GET, "/api/listarProdutos").hasAnyAuthority("ADMINISTRADOR", "ESTOQUISTA")
                        .requestMatchers(HttpMethod.GET, "/api/usuariosPorPesquisa").hasAuthority("ADMINISTRADOR")
                        .requestMatchers(HttpMethod.GET, "/api/buscaUsuario").hasAuthority("ADMINISTRADOR")
                        .requestMatchers(HttpMethod.GET, "/api/produtos/recuperaProduto").hasAnyAuthority("ADMINISTRADOR", "ESTOQUISTA")
                        .requestMatchers(HttpMethod.GET, "/api/produtos/listagem").hasAnyAuthority("ADMINISTRADOR", "ESTOQUISTA")
                        .requestMatchers(HttpMethod.GET, "/api/produtos/buscarNome").hasAuthority("ADMINISTRADOR")
                        .requestMatchers(HttpMethod.GET, "/api/produtos").permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .cors(withDefaults()); // Adiciona suporte a CORS

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder encriptadorSenha() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration
                                                               authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
