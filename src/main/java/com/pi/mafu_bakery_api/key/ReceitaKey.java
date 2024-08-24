package com.pi.mafu_bakery_api.key;

import com.pi.mafu_bakery_api.model.MateriaPrima;
import com.pi.mafu_bakery_api.model.Produto;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@EqualsAndHashCode
@Embeddable
public class ReceitaKey {

    @ManyToOne
    @JoinColumn(name = "fk_produto_id")
    private Produto produto_id;
    @ManyToOne
    @JoinColumn(name = "fk_materia_prima_id")
    private MateriaPrima materiaPrima_id;

}
