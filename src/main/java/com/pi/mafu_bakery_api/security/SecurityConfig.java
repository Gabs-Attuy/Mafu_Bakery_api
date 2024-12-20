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

        final String [] ENDPOINTS_LIBERADOS = {
                "/api/usuario",
                "/api/auth/signin",
                "/api/cliente",
                "/api/usuarioLogado",
                "/api/produtos",
                "/api/produtos/exibirTodos",
                "/api/produtos/exibicao",
                "/api/cliente/cadastro",
                "/api/cliente/alterarCliente",
                "/api/cliente/infoCliente",
                "/api/endereco/cadastrar",
                "/api/endereco/enderecosCliente",
                "/api/endereco/enderecoPrincipal",
                "/api/endereco/enderecosEntrega"
        };

        final String [] ENDPOINTS_ADMINISTRADOR = {
                "/api/ativaDesativaUsuario",
                "/api/alterarUsuario",
                "/api/mp/statusMp",
                "/api/mp/statusProduto",
                "/api",
                "/api/usuariosPorPesquisa",
                "/api/buscaUsuario",
                "/api/produtos/alterar",
                "/api/produtos/cadastrar"
        };

        final String [] ENDPOINTS_ESTOQUISTA = {
                "/api/mp/aumentarMp",
                "/api/mp/consumirMp",
                "/api/produtos/confeccionaProdutos",
                "/api/listarPedidos",
                "/api/pedidos/atualizarStatus"
        };

        final String [] ENDPOINTS_ADMINISTRADOR_ESTOQUISTA = {
                "/api/mp",
                "/api/listarProdutos",
                "/api/produtos/recuperaProduto",
                "/api/produtos/listagem",
                "/api/produtos/buscarNome",
                "/api/mp/mpPorId",
                "/api/pedidos/listarPedidosBackOffice"
        };

        final String [] ENDPOINTS_CLIENTE = {
                "/api/cliente/alteraSenha",
                "/api/pedidos/realizarPedido",
                "/api/pedidos/listarPedidos"
        };

                http
                .csrf(csfr -> csfr.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                          .requestMatchers(ENDPOINTS_ESTOQUISTA).hasAuthority("ESTOQUISTA")
                          .requestMatchers(ENDPOINTS_ADMINISTRADOR).hasAuthority("ADMINISTRADOR")
                          .requestMatchers(ENDPOINTS_ADMINISTRADOR_ESTOQUISTA).hasAnyAuthority("ADMINISTRADOR", "ESTOQUISTA")
                          .requestMatchers(ENDPOINTS_CLIENTE).hasAuthority("CLIENTE")
                          .requestMatchers(ENDPOINTS_LIBERADOS).permitAll()
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
