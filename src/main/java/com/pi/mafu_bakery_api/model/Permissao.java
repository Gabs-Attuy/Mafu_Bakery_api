package com.pi.mafu_bakery_api.model;

import com.pi.mafu_bakery_api.enums.PermissaoEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Entity
public class Permissao implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private PermissaoEnum descricao;

    public Permissao(Optional<Usuario> usuarioRecuperado, PermissaoEnum permissaoEnum) {
    }

    @Override
    public String getAuthority() {
        return this.descricao.name();
    }
}
