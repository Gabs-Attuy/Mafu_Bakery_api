package com.pi.mafu_bakery_api.key;

import com.pi.mafu_bakery_api.model.Permissao;
import com.pi.mafu_bakery_api.model.Usuario;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Embeddable
@EqualsAndHashCode
public class PermissaoUsuarioKey {

    @ManyToOne
    @JoinColumn(name = "fk_permissao_id")
    private Permissao permissao;

    @ManyToOne
    @JoinColumn(name = "fk_usuario_id")
    private Usuario usuario;
}
