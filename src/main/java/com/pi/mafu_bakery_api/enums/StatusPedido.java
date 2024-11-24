package com.pi.mafu_bakery_api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusPedido {
    AGUARDANDO_PAGAMENTO("AGUARDANDO PAGAMENTO"),
    PAGAMENTO_REJEITADO("PAGAMENTO REJEITADO"),
    PAGAMENTO_SUCESSO("PAGAMENTO COM SUCESSO"),
    AGUARDANDO_RETIRADA("AGUARDANDO RETIRADA"),
    EM_TRANSITO("EM TRÂNSITO"),
    ENTREGUE("ENTREGUE");

    private String status;
}
