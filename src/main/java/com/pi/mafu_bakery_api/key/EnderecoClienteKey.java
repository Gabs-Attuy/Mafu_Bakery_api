package com.pi.mafu_bakery_api.key;

import com.pi.mafu_bakery_api.model.Cliente;
import com.pi.mafu_bakery_api.model.Endereco;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Embeddable
@EqualsAndHashCode
public class EnderecoClienteKey {

    @ManyToOne
    @JoinColumn(name = "fk_enderecoId")
    private Endereco enderecoId;
    @ManyToOne
    @JoinColumn(name = "fk_usuarioId")
    private Cliente clienteId;



}
