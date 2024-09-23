package com.pi.mafu_bakery_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class ExibicaoImagemDTO {

    private String url;
    private Boolean principal;

}
