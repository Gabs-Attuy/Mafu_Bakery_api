package com.pi.mafu_bakery_api.model;

import com.pi.mafu_bakery_api.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Entity
public class Permissao implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private RoleEnum permissao;
    @OneToMany(mappedBy = "permissao")
    private List<Credencial> credencial;

    public Permissao(Optional<Usuario> usuarioRecuperado, RoleEnum permissaoEnum) {
    }

    @Override
    public String getAuthority() {
        return this.permissao.name();
    }
}
