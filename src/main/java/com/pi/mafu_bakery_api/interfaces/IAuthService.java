package com.pi.mafu_bakery_api.interfaces;

import com.pi.mafu_bakery_api.dto.CredenciaisContaDTO;
import org.springframework.http.ResponseEntity;

public interface IAuthService {

    ResponseEntity<?> autenticacao(CredenciaisContaDTO dto) throws Exception;
}
