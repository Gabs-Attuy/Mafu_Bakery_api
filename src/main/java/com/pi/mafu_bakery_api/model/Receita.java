package com.pi.mafu_bakery_api.model;

import com.pi.mafu_bakery_api.key.ReceitaKey;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Digits;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Entity
@Component
public class Receita {

    @EmbeddedId
    private ReceitaKey id;
    @Column(nullable = false)
    @Digits(integer = 2, fraction = 3)
    private BigDecimal quantidadeNecessaria;

}
