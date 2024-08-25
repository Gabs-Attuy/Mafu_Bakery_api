package com.pi.mafu_bakery_api.key;

import com.pi.mafu_bakery_api.model.Endereco;
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
public class EnderecoUsuarioKey {

    @ManyToOne
    @JoinColumn(name = "fk_endereco_id")
    private Endereco endereco_id;
    @ManyToOne
    @JoinColumn(name = "fk_usuario_id")
    private Usuario usuario_id;

}
