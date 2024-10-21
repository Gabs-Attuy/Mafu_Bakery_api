package com.pi.mafu_bakery_api.model;

import com.pi.mafu_bakery_api.key.EnderecoClienteKey;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Entity
@Component
public class EnderecoCliente {

    @EmbeddedId
    private EnderecoClienteKey id;

    @ManyToOne
    @JoinColumn(name = "fk_enderecoId", insertable = false, updatable = false)
    private Endereco endereco;

    @ManyToOne
    @JoinColumn(name = "fk_usuarioId", insertable = false, updatable = false)
    private Cliente cliente;

}
