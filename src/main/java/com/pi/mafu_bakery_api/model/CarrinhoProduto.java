package com.pi.mafu_bakery_api.model;

import com.pi.mafu_bakery_api.key.CarrinhoProdutoKey;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
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
public class CarrinhoProduto {

    @EmbeddedId
    private CarrinhoProdutoKey id;
    private Integer quantidade;
}