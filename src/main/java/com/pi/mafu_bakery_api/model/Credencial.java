package com.pi.mafu_bakery_api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Entity
public class Credencial implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String senha;
    private Boolean isAccountNonExpired;
    private Boolean isAccountNonLocked;
    private Boolean isCredentialsNonExpired;
    private Boolean isEnabled;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "permissao_usuario", joinColumns = {@JoinColumn(name = "credencial_id")},
            inverseJoinColumns = {@JoinColumn(name = "usuario_id")})
    private List<Permissao> permissoes;

    public List<String> getRoles() {
        List<String> roles = new ArrayList<>();
        for (Permissao permissao : permissoes) {
            roles.add(permissao.getDescricao().name());
        }
        return roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.permissoes;
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return Boolean.TRUE.equals(this.isAccountNonExpired);
    }

    @Override
    public boolean isAccountNonLocked() {
        return Boolean.TRUE.equals(this.isAccountNonLocked);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return Boolean.TRUE.equals(this.isCredentialsNonExpired);
    }

    @Override
    public boolean isEnabled() {
        return Boolean.TRUE.equals(this.isEnabled);
    }
}
