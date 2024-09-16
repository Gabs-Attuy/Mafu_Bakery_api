package com.pi.mafu_bakery_api.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class IngredienteDTO {

    private Long id;
    @Column(nullable = false)
    private Double quantidade;
}
